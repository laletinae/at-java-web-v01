package work.part02;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AuthorizationTests {
    @Test
    public void test01LoginSuccess() {
        open("https://slqamsk.github.io/cases/slflights/v01/");


        SelenideElement fieldLogin = $("input[id=username]");
        SelenideElement fieldPassword = $("input[id=password]");
        SelenideElement btnLogin = $("button[id=loginButton]");

        SelenideElement menuMessage = $("span[id=greeting]");

        fieldLogin.type("standard_user");
        fieldPassword.type("stand_pass1");

        btnLogin.click();

        menuMessage.shouldHave(text("Добро пожаловать, Иванов Иван Иванович!"));

        sleep(1000);

    }
    @Test
    public void test02LoginWrongPassword() {
        open("https://slqamsk.github.io/cases/slflights/v01/");


        SelenideElement fieldLogin = $("input[id=username]");
        SelenideElement fieldPassword = $("input[id=password]");
        SelenideElement btnLogin = $("button[id=loginButton]");

        SelenideElement welcomeMessage = $("div[id=message]");

        fieldLogin.type("standard_user");
        fieldPassword.type("stand_pass1_wrong");

        btnLogin.click();

        welcomeMessage.shouldHave(text("Неверное имя пользователя или пароль."));

        sleep(1000);
    }
}
