package demo.part02;
import com.codeborne.selenide.Browsers;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@TestMethodOrder(MethodOrderer.MethodName.class)
class AuthorizationTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.browser = Browsers.FIREFOX;
    }

    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/cases/slflights/v01/");
    }

    @Test
    public void test01LoginSuccess() {

        SelenideElement myElement = $("input[name+username]");
        $(By.id("username")).sendKeys("standard_user");
        $(By.id("password")).type("stand_pass1");
        $(By.id("loginButton")).click();
        $(By.className("greeting")).shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

    }

    @Test
    public void test02LoginWrongPassword() {

        SelenideElement myElement = $("input[name+username]");
        $(By.id("username")).setValue("standard_user");
        $(By.id("password")).setValue("asdfasdfasdfasdfasdf");
        $(By.id("loginButton")).click();
        $(By.className("error")).shouldHave(text("Неверное имя пользователя или пароль."));
    }


}
