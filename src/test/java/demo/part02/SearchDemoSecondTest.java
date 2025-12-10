package demo.part02;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class SearchDemoSecondTest {
    @Test
    public void testElementSearchMethods() {
        open("https://slqamsk.github.io/demo/search-demo/");
        $(By.id("submit-button")).shouldBe(visible);
        $("#submit-button").shouldBe(visible);

        $(By.name("interests")).shouldBe(visible);
        $("[name=interests]").shouldBe(visible);

        $(By.className("nav-link")).shouldBe(visible);
        $(".nav-link").shouldBe(visible);

        $(By.tagName("input")).shouldBe(visible);
        $("input").shouldBe(visible);

        $(By.linkText("Контакты и обратная связь")).shouldBe(visible);
        //Нет аналогов в CSS

        $(By.partialLinkText("длинный")).shouldBe(visible);
        //Нет аналогов в CSS
    }
}