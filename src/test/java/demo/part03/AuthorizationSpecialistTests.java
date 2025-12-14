package demo.part03;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;

import java.util.Random;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

//@SuppressWarnings({"Connection reset","Unable to find CDP implementation matching 143"})
//Обычно методы вызываются случайным образом. Аннотация TestMethodOrder позволяет запускать в порядке, отсортированном по имени класса
@TestMethodOrder(MethodOrderer.MethodName.class) public class AuthorizationSpecialistTests {

    //Поиск элементов по CSS-селектору
    SelenideElement fieldLogin = $("input[id=Email1]");
    SelenideElement fieldPassword = $("input[id=Password]");
    SelenideElement btnLogin = $("button.submit-button");
    SelenideElement welcomeMessage = $("form[id=form-logon1] div ul li"); //Сообщение под формой входа


    SelenideElement errorMessage = $(".validation-summary-errors"); //Сообщение об ошибках логин/пароль (при пустом вводе не появляется)

    String[] browsers = {"chrome", "chrome"}; //Браузер будет выбираться случайным образом для каждого теста

    String pageUrl = "https://www.specialist.ru/account/logon";


    /**
     * Пример запуска кода перед каждым тестом
     * Написать название теста, который начал выполнение
     * Случайным образом выбрать браузер, в котором будем тестировать
     *
     */
    @BeforeEach
    void chooseBrowser(TestInfo test_info) {

        System.out.println("Тест " + test_info.getDisplayName() + " - начали выполнение.");

        // Создаем объект Random
        Random random = new Random();

        // Генерируем случайный индекс
        // nextInt(fruits.length) вернет число от 0 (включительно) до длины массива (исключая ее)
        int randomIndex = random.nextInt(browsers.length);

        // Получаем случайный элемент (выбранный браузер)
        Configuration.browser = browsers[randomIndex];

        System.out.println("Браузер " + Configuration.browser);

        open(pageUrl);

        System.out.println("Страница " + pageUrl);
    }


    /**
     * Пример запуска кода после каждого теста
     * Написать название теста, который закончил выполнение
     * Закруть выбранный случайным образом браузер
     *
     */
    @AfterEach
    void printTestNameEnd(TestInfo test_info) {
        System.out.println("Тест " + test_info.getDisplayName() + " - закончили.");

        closeWindow();

    }

    /**
     * Вводится неврный логин и пароль
     * Проверить верный - нет возможности, т.к. данные в открытом виде
     *
     */
    @Test
    @DisplayName("Это человеко-читаемое название теста: вводим НЕверный логин и НЕверный пароль")
    public void test01LoginSpecialist() {

        fieldLogin.setValue("standard_user_wrong");
        fieldPassword.setValue("stand_pass1_wrong");

        btnLogin.click();

        //При НЕверном вводе логина и пароля - сообщение под формой ввода должно быть такое:
        welcomeMessage.shouldHave(text("Почта, номер телефона или пароль не верны."));

        sleep(500);
    }

    /**
     * Вводится пустой логин и пароль
     * Проверить верный - нет возможности, т.к. данные в открытом виде
     *
     */
    @Test
    @DisplayName("Это человеко-читаемое название теста: вводим пустой логин и пустой пароль")
    public void test02LoginSpecialist() {

        fieldLogin.setValue("");
        fieldPassword.setValue("");

        btnLogin.click();

        //При пустом вводе форма не отправляется (кнопка не должна кликаться) - т.к. поле required
        //Как это проверить?? Это корректная проверка?
        errorMessage.shouldNot(exist);

        sleep(500);
    }
}
