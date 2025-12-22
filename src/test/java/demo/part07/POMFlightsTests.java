package demo.part07;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import demo.part07.pages.FlightsListPage;
import demo.part07.pages.LoginPage;
import demo.part07.pages.RegistrationPage;
import demo.part07.pages.SearchPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = "firefox";
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
        sleep(10_000);
    }
    // ... Автотесты
    // 1. Неуспешный логин
    @Test
    void test01WrongPassword() {
        LoginPage myLoginPage = new LoginPage();
        myLoginPage.login("standard_user", "WrongPassword");
        myLoginPage.isLoginUnsuccessful();
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
        searchPage.search("16.03.2026", "Казань", "Париж");

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
        searchPage.search("16.03.2026", "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.registerToFirstFlight();

        // Страница регистрации на рейс
        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.successRegistration();
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
        searchPage.search("16.03.2026", "Москва", "Нью-Йорк");

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
    @CsvFileSource (resources = "logins.csv")
    void test06MuliLogin(String userName, String passWord, String fio) {
        LoginPage lp = new LoginPage();
        lp.login(userName,passWord);
        lp.isLoginSuccessful(fio);
        sleep(5000);
    }

    // 7. Проверка, что при поиске даты в прошлом - ошибка

    @Test
    void test07DateInPast() {
        LoginPage lp = new LoginPage();
        lp.login("standard_user", "stand_pass1");

        SearchPage sp = new SearchPage();
        sp.search("01.01.2024");
        sp.isDateInPast();
    }

    //(*) Напишите автотест для теста:
    // "Поиск - не найдены рейсы -
    // возврат на страницу поиска - найдены рейсы -
    // Регистрация на 1-й рейс в списке - не задан номер паспорта -
    // повторный ввод паспорта с корректными данными - успешная регистрация."

    @Test
    void test08LongTest() {
        LoginPage lp = new LoginPage();
        lp.login("standard_user", "stand_pass1");

        // "Поиск - не найдены рейсы -
        SearchPage searchPage = new SearchPage();
        searchPage.search("16.03.2026", "Казань", "Париж");

        FlightsListPage flightsList = new FlightsListPage();
        flightsList.isNoFlights();

        // возврат на страницу поиска - найдены рейсы -
        flightsList.newSearch();
        searchPage.search("16.03.2026", "Москва", "Нью-Йорк");

        // Регистрация на 1-й рейс в списке - не задан номер паспорта -
        flightsList.registerToFirstFlight();

        RegistrationPage registrationPage = new RegistrationPage();
        registrationPage.isFlightDataCorrect("Москва", "Нью-Йорк");
        registrationPage.registration("Иван Петров", "", "test@mail.ru", "+79999999999");
        registrationPage.isErrorFillAllFied();

        // повторный ввод паспорта с корректными данными - успешная регистрация."
        registrationPage.registration("Иван Петров", "1234 123456", "test@mail.ru", "+79999999999");
        registrationPage.successRegistration();
    }

    @Test
    void test09DemoCollection() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");

        SearchPage searchPage = new SearchPage();
        searchPage.search("16.03.2026", "Москва", "Нью-Йорк");

        // Страница со списком найденных рейсов
        FlightsListPage flightsList = new FlightsListPage();
        flightsList.sortByPrice();
        flightsList.isTimeSorted();
    }
}