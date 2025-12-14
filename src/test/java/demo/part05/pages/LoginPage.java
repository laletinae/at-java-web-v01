package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

public class LoginPage {
    SelenideElement
            username,
            password,
            loginButton,
            errorMessage,
            greeting;

    public LoginPage() {
        username = $("#username");
        password = $("#password");
        loginButton = $("#loginButton");
        errorMessage = $("#message");
        greeting = $("#greeting");
    }

    @Step("Вход в систему")
    public void login(String username, String password) {
        this.username.setValue(username);
        this.password.setValue(password);
        this.loginButton.click();
    }

    @Step("Неуспешный логин")
    public void isLoginUnsuccessful() {
        this.errorMessage.shouldHave(text("Неверное имя пользователя или пароль."));
    }

    @Step("Успешный логин")
    public void isLoginSuccessful(String fio) {
        this.greeting.shouldHave(text("Добро пожаловать, " + fio + "!"));
    }

}