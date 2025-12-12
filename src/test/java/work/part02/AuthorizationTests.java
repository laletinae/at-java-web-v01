package work.part02;

import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;


public class AuthorizationTests {

    //Поиск элементов по CSS-селектору
    SelenideElement fieldLogin = $("input[id=username]");
    SelenideElement fieldPassword = $("input[id=password]");
    SelenideElement btnLogin = $("button[id=loginButton]");
    SelenideElement menuMessage = $("span[id=greeting]"); //Сообщение на панели верхнего меню
    SelenideElement welcomeMessage = $("div[id=message]");  //Сообщение под формой входа
    /**
     * Пример запуска кода перед всеми тестами (например, выбор браузера и открытие страницы)
     *
     */
    @BeforeAll
    static void setup() {
        //Тестируемая страница
        var pageUrl = "https://slqamsk.github.io/cases/slflights/v01/";


        //Допустимы 2 варианта записи "firefox" или Browsers.FIREFOX
        Configuration.browser = "firefox";
        //Configuration.browser = Browsers.FIREFOX;

        /*
        //Другие браузеры:
        Configuration.browser = "chrome";
        Configuration.browser = Browsers.CHROME;
        Configuration.browser = "edge";
        Configuration.browser = Browsers.EDGE;
        */

        System.out.println("Браузер " + Configuration.browser);

        open(pageUrl);

        System.out.println("Страница " + pageUrl);
    }

    /**
     * Пример запуска кода перед каждым тестом (например, написать название теста, который начал выполнение)
     *
     */
    @BeforeEach
    void printTestNameBegin(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - начали выполнение.");
    }

    /**
     * Пример запуска кода после каждого теста (например, написать название теста, который закончил выполнение)
     *
     */
    @AfterEach
    void printTestNameEnd(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - закончили.");
    }


    /**
     * Вводим верный логин и пароль, проверяем что в сообщении приветствия - нужный пользователь
     *
     */
    @Test
    public void test01LoginSuccess() {
        open("https://slqamsk.github.io/cases/slflights/v01/");

        fieldLogin.setValue("standard_user");
        fieldPassword.setValue("stand_pass1");
        btnLogin.click();

        //При верном вводе логина и пароля - сообщение в панели меню должно быть такое:
        menuMessage.shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        sleep(1000);

    }

    /**
     * Вводим верный логин и пароль, проверяем что в сообщении приветствия - нужный пользователь
     *
     */
    @Test
    public void test02LoginWrongPassword() {

        fieldLogin.setValue("standard_user");
        fieldPassword.setValue("stand_pass1_wrong");
        btnLogin.click();

        //При НЕверном вводе логина и пароля - сообщение под формой ввода должно быть такое:
        welcomeMessage.shouldHave(text("Неверное имя пользователя или пароль."));

        sleep(500);
    }
}
