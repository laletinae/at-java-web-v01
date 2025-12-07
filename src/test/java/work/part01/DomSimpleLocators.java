package work.part01;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DomSimpleLocators {
    @Test
    public void testSearchBy () {

        open("https://slqamsk.github.io/demo/search-demo/");

        $(By.id("submit-button")).shouldBe(visible);
        $(By.id("user-password")).shouldBe(visible);


        $(By.name("interests")).shouldBe(visible);
        $(By.name("user_email")).shouldBe(visible);

        $(By.className("nav-link")).shouldBe(visible);
        $(By.className("btn")).shouldBe(visible);


        $(By.tagName("input")).shouldBe(visible);
        $(By.tagName("h3")).shouldBe(visible);

        $(By.linkText("Контакты и обратная связь")).shouldBe(visible);
        $(By.linkText("Регистрация нового пользователя в системе")).shouldBe(visible);

        $(By.partialLinkText("длинный")).shouldBe(visible);
        $(By.partialLinkText("Контакты")).shouldBe(visible);

    }
}
