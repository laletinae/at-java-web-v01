package work.part07;

import com.codeborne.selenide.logevents.SelenideLogger;
import work.part07.pages.FlightsListPage;
import work.part07.pages.LoginPage;
import work.part07.pages.RegistrationPage;
import work.part07.pages.SearchPage;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;


@TestMethodOrder(MethodOrderer.DisplayName.class)
public class POMFlightsTests {
    @BeforeAll
    static void beforeAll() {
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

    // Напишите автотест для теста: "Убедиться, что при попытке найти рейс для даты в прошлом отображается ошибка"
    @Test
    void test06DateInPast() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов
        SearchPage searchPage = new SearchPage();
        searchPage.search("16.03.2025", "Москва", "Нью-Йорк");
        searchPage.isDepartureDateInPast();
    }

    // Поиск - не найдены рейсы - возврат на страницу поиска - найдены рейсы -
    // Регистрация на 1-й рейс в списке - не задан номер паспорта - повторный ввод паспорта с корректными данными - успешная регистрация.
    @Test
    void test06ComplicatedSearch() {
        // Страница логина
        LoginPage loginPage = new LoginPage();
        loginPage.login("standard_user", "stand_pass1");
        loginPage.isLoginSuccessful("Иванов Иван Иванович");

        // Страница поиска рейсов - рейсов не найдено
        SearchPage searchPage = new SearchPage();
        searchPage.search("22.12.2025", "Казань", "Париж");
        searchPage.isNoFlightsFound();

        //Возврат на страницу поиска
        searchPage.newSearch();

        //Новый поиск - рейсы найдены
        searchPage.search("22.12.2025", "Москва", "Нью-Йорк");
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
}