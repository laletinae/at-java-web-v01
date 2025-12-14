package work.part02;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

/**
 * Пример работы с параметризованным тестом для тестирования формы авторизации
 * Использование аннотаций ParameterizedTest, CsvSource
 * и BeforeAll, BeforeEach, AfterEach
 *
 */
public class AuthorizationParameterizedTests {

    //Поиск элементов по CSS-селектору
    SelenideElement fieldLogin = $("input[id=username]");
    SelenideElement fieldPassword = $("input[id=password]");
    SelenideElement btnLogin = $("button[id=loginButton]");
    SelenideElement menuMessage = $("span[id=greeting]"); //Сообщение на панели верхнего меню
    SelenideElement welcomeMessage = $("div[id=message]");  //Сообщение под формой входа

    static String pageUrl = "https://slqamsk.github.io/cases/slflights/v01/";
    /**
     * Пример запуска кода перед всеми тестами (например, выбор браузера и открытие страницы)
     *
     */
    @BeforeAll
    static void setup() {
        Configuration.browser = "firefox";
        System.out.println("Браузер " + Configuration.browser);
    }

    /**
     * Пример запуска кода перед каждым тестом (например, написать название теста, который начал выполнение)
     *
     */
    @BeforeEach
    void printTestNameBegin(TestInfo test_info) {
        System.out.println("Начало " + test_info.getDisplayName());
        open(pageUrl);
        System.out.println("Страница " + pageUrl);
    }

    /**
     * Пример запуска кода после каждого теста (например, написать название теста, который закончил выполнение)
     *
     */
    @AfterEach
    void printTestNameEnd(TestInfo test_info) {

        closeWindow();
        System.out.println("Конец " + test_info.getDisplayName());
    }


    /**
     * Источник данных для параметризованного теста: логин, пароль и ФИО для каждого пользователя
     * Проверяются только существующие пользователи, с правильным паролем
     *
     */
    @ParameterizedTest (name = "01. Успешный вход в систему, #{index}, username: {0}")
    @CsvSource({
            "standard_user, stand_pass1, 'Иванов Иван Иванович'",
            "problem_user, prob_pass3, 'Сидорова Анна Владимировна'",
            "error_user, erro_pass5, 'Смирнова Елена Александровна'",
            "performance_glitch_user, perf_pass4, 'Кузнецов Дмитрий Сергеевич'",
            "visual_user, visu_pass6, 'Федоров Алексей Николаевич'"
    })
    public void test01LoginSuccess(String username, String password, String fio) {

        fieldLogin.setValue(username);
        fieldPassword.setValue(password);
        btnLogin.click();

        //При верном вводе логина и пароля - сообщение в панели меню должно быть такое:
        menuMessage.shouldHave(text("Добро пожаловать, " + fio + "!"));

        sleep(500);

    }

    /**
     * Источник данных для параметризованного теста: логин, пароль и ФИО для каждого пользователя
     * Проверяются заблокированные пользователи, с правильным паролем
     *
     */
    @ParameterizedTest (name = "02. Заблокированный пользователь, #{index}, username: {0}")
    @CsvSource({
            "locked_out_user, lock_pass2, 'Петров Петр Петрович'"
    })
    public void test02LoginBlocked(String username, String password, String fio) {

        fieldLogin.setValue(username);
        fieldPassword.setValue(password);
        btnLogin.click();

        //Для заблокированного пользователя - сообщение под формой ввода должно быть такое:
        welcomeMessage.shouldHave(text("Пользователь заблокирован."));

        sleep(500);
    }

    /**
     * Источник данных для параметризованного теста: логин, пароль и ФИО для каждого пользователя
     * Проверяются заблокированные пользователи, с правильным паролем
     *
     */
    @ParameterizedTest (name = "03. Неверный логин или пароль, #{index}, username: {0}")
    @CsvSource({
            "standard_user, stand_pass_wrong, 'Иванов Иван Иванович'",
            "locked_out_user, lock_pass_wrong, 'Петров Петр Петрович'"
    })
    public void test03LoginWrongPassword(String username, String password, String fio) {

        fieldLogin.setValue(username);
        fieldPassword.setValue(password);
        btnLogin.click();

        //При НЕверном вводе логина и пароля - сообщение под формой ввода должно быть такое:
        welcomeMessage.shouldHave(text("Неверное имя пользователя или пароль."));

        sleep(500);
    }
}
