package work.part07;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class FlightsTests {
    @BeforeAll
    static void beforeAll(TestInfo test_info) {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        open("https://slqamsk.github.io/cases/slflights/v01/");
        getWebDriver().manage().window().maximize();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @Test
    void test01FailLogin() {
        $("#username").setValue("wrong login");
        $("#password").setValue("wrong pass");
        $("#loginButton").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль."));

        sleep(5000);

    }
    @Test
    void test02SearchNoDate() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        $("#departureDate").setValue("");

        $x("//button[contains(.,'Найти')]").click();

        $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));

        sleep(5000);

    }

    @Test
    void test03NoFlightsFound() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        $("#departureDate").setValue("2025-12-22");//firefox
        //$("#departureDate").setValue("22.12.2025");//chrome
        $("#departureCity").selectOption("Казань");
        $("#arrivalCity").selectOption("Париж");

        $x("//button[contains(.,'Найти')]").click();

        $x("//*[@id='flightsContainer']//td[contains(.,'Рейсов по вашему запросу не найдено.')]").should(exist);

        sleep(10000);

    }

    @Test
    void test04NoRegistration() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        $("#departureDate").setValue("2025-12-22");
        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Нью-Йорк");

        $x("//button[contains(.,'Найти')]").click();

        $x("//button[@class='register-btn']").click();

        $("#passportNumber").setValue("12a4 567890");

        $x("//button[contains(.,'Завершить регистрацию')]").click();

        $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));

        //$x("//*[@id='flightsContainer']//td[contains(.,'Рейсов по вашему запросу не найдено.')]").should(exist);

        sleep(10000);

    }


    @Test
    void test05SuccessRegistration() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        $("#departureDate").setValue("2025-12-22");
        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Нью-Йорк");

        $x("//button[contains(.,'Найти')]").click();

        $x("//button[@class='register-btn']").click();


        $x("//button[contains(.,'Завершить регистрацию')]").click();

        $("#registrationMessage").shouldHave(text("Регистрация успешно завершена!"));


        sleep(10000);

    }
}
