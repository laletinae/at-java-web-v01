import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class SimpleFlightsTests {

    @BeforeEach
    void setUp() {
        open("https://slqa.ru/cases/DeepSeekFlights/");
        getWebDriver().manage().window().maximize();
    }

    //Автотесты
    // 1. Неуспешный логин
    @Test
    void test01WrongPassword() {
        $("#username").setValue("standard_user");
        $("#password").setValue("WrongPassword");
        $("#loginButton").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль."));
    }

    // 2. Не задана дата
    @Test
    void test02NoDate() {
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        $("#departureDate").setValue("");
        $x("//button[.='Найти']").click();
        $("#searchMessage").shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    // 3. Не найдены рейсы
    @Test
    void test03FlightsNotFound() {
        //Страница логина
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        //Страница поиска
        $("#departureCity").selectOption("Казань");
        $("#arrivalCity").selectOption("Париж");
        $("#departureDate").setValue("24.11.2025");
        $x("//button[.='Найти']").click();

        //Страница списка рейсов
        $("#flightsTable").shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    //4. Неправильный номер паспорта
    @Test
    void test04WrongPassportNumber() {
        //Страница логина
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        //Страница поиска
        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Нью-Йорк");
        $("#departureDate").setValue("24.11.2025");
        $x("//button[.='Найти']").click();

        //Страница списка рейсов
        $x("//button[.='Зарегистрироваться']").click();

        //Страница регистрации
        $("#passportNumber").setValue("паспорт");
        $x("//button[.='Завершить регистрацию']").click();
        $("#registrationMessage").shouldHave(text("Номер паспорта должен содержать только цифры и пробелы."));
    }

    //5. Успешная регистрация
    @Test
    void test05SuccessRegistration() {
        //Страница логина
        $("#username").setValue("standard_user");
        $("#password").setValue("stand_pass1");
        $("#loginButton").click();
        $("#greeting").shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        //Страница поиска
        $("#departureCity").selectOption("Москва");
        $("#arrivalCity").selectOption("Нью-Йорк");
        $("#departureDate").setValue("24.11.2025");
        $x("//button[.='Найти']").click();

        //Страница списка рейсов
        $x("//button[.='Зарегистрироваться']").click();

        //Страница регистрации
        $x("//button[.='Завершить регистрацию']").click();
        Alert alert= switchTo().alert();
        assertTrue(alert.getText().contains("Бронирование завершено"));
        alert.accept();
        $("#registrationMessage").shouldHave(text("Регистрация успешно завершена!"));
    }
}