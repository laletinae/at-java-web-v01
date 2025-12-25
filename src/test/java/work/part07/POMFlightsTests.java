package work.part07;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import work.part07.pages.FlightsListPage;
import work.part07.pages.LoginPage;
import work.part07.pages.RegistrationPage;
import work.part07.pages.SearchPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static java.lang.System.exit;


@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
    }
    // ... Автотесты
    // 1. Неуспешный логин
    @Test
    void test01WrongPassword() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "WrongPassword");
        loginPage.isLoginUnsuccessful();
    }

    // 2. Не задана дата
    @Test
    void test02NoDate() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search("");
        searchPage.isDepartureDateEmpty();
    }
    // 3. Не найдены рейсы
    @Test
    void test03FlightsNotFound() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Казань", "Париж");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.isNoFlights();
    }

    //4. Успешная регистрация с данными по умолчанию
    @Test
    void test04SuccessRegistrationDefault() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        // Страница регистрации на рейс
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.successDefaultRegistration();
    }

    // 5. Пустые поля
    @Test
    void test05EmptyField() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        // Страница регистрации на рейс
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.registration("", "", "", "");
        registrationPage.isErrorFillAllFied();
    }

    // 6. Успешный логин под разными пользователями.
    @ParameterizedTest
    @CsvFileSource(resources = "logins.csv")
    void test06MuliLogin(String userName, String passWord, String fio) {
        demo.part07.pages.LoginPage lp = new demo.part07.pages.LoginPage();
        lp.login(userName,passWord);
        lp.isLoginSuccessful(fio);
        sleep(5000);
    }

    // 7. Напишите автотест для теста: "Убедиться, что при попытке найти рейс для даты в прошлом отображается ошибка"
    @Test
    void test07DateInPast() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Москва", "Нью-Йорк");
        searchPage.isDepartureDateInPast();
    }

    // 8. Поиск - не найдены рейсы - возврат на страницу поиска - найдены рейсы -
    // Регистрация на 1-й рейс в списке - не задан номер паспорта -
    // повторный ввод паспорта с корректными данными - успешная регистрация.
    @Test
    void test08ComplicatedSearch() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов - рейсов не найдено
        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Казань", "Париж");
        searchPage.isNoFlightsFound();

        //Возврат на страницу поиска
        searchPage.newSearch();

        //Новый поиск - рейсы найдены
        searchPage.search(getDateByWeekDay(1), "Москва", "Нью-Йорк");
        searchPage.isFlightsFound();

        //Регистрация на первый рейс в списке
        searchPage.openReg();
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.registration("");
        registrationPage.isErrorFillAllFied();

        //Возврат к найденным рейсам
        registrationPage.returnToFlights();


        //Повторная регистрация с корректным паспортом (значения по умолчанию)
        searchPage.openReg();
        registrationPage.successDefaultRegistration();
    }


    // 9. Проверить что найденные рейсы сортируются всеми возвожными способами:
    // по цене, по времени, по возрастанию, по убыванию
    @Test
    void test09CheckSorted() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов - найдены рейсы
        SearchPage searchPage = new SearchPage();
        searchPage.search(getDateByWeekDay(1), "Москва", "Нью-Йорк");
        searchPage.isFlightsFound();

        // Страница со списком найденных рейсов
        FlightsListPage flightsList = new FlightsListPage();

        //Сортировка по времени
        flightsList.sortByTime("asc");
        flightsList.isTimeAscSorted();
        flightsList.sortByTime("desc");
        flightsList.isTimeDescSorted();

        //Сортировка по цене
        flightsList.sortByPrice("asc");
        flightsList.isPriceAscSorted();
        flightsList.sortByPrice("desc");
        flightsList.isPriceDescSorted();

    }

    /**
     * Вспомогательная функция - найти ближайший день недели к текущей дате
     */
    static String getDateByWeekDay(int weekDay) {
        // Приводим к стандарту Java (1-7 = понедельник-воскресенье)
        DayOfWeek targetDay = DayOfWeek.of(weekDay);
        // Получаем текущую дату
        LocalDate today = LocalDate.now();

        // Находим ближайшую дату с нужным днём недели, начиная со следующего дня
        LocalDate resultDate = today.plusDays(1).with(TemporalAdjusters.nextOrSame(targetDay));

        // Создаем форматировщик в зависимости от браузера
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        //конструкция switch со значением по умолчанию
        formatter = switch (Configuration.browser) {
            case "firefox" ->
                    DateTimeFormatter.ofPattern("yyyy-MM-dd");
            case "chrome" ->
                    DateTimeFormatter.ofPattern("dd.MM.yyyy");
            default -> formatter;
        };


        // Форматируем в строку
        String dateStr = resultDate.format(formatter);

        System.out.println(dateStr);
        return dateStr;
    }

    // 10. Проверить автозаполнение данных пользователя
    @Test
    void test10CheckRegistrationDefaultFields() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();

        //Поиск - рейсы найдены (Москва-Нью-Йорк, вторник - 1 рейс)
        searchPage.search(getDateByWeekDay(2), "Москва", "Нью-Йорк");
        searchPage.isFlightsFound();

        //Регистрация на первый рейс в списке
        searchPage.openReg();
        RegistrationPage registrationPage = new RegistrationPage();

        //Проверка автозаполнения полей формы регистрации
        registrationPage.checkDefaultFields();
    }

    // 11. Проверить валидацию поля ФИО (только русские буквы, пробелы и дефис)
    @Test
    void test11CheckFioValidation() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();

        //Поиск - рейсы найдены (Москва-Нью-Йорк, вторник - 1 рейс)
        searchPage.search(getDateByWeekDay(2), "Москва", "Нью-Йорк");
        searchPage.isFlightsFound();

        //Регистрация на первый рейс в списке
        searchPage.openReg();
        RegistrationPage registrationPage = new RegistrationPage();

        //Проверка валидации ФИО
        registrationPage.checkFioValidation();
    }



}