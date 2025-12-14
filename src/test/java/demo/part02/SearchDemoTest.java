package demo.part02;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
public class SearchDemoTest {
    @Test
    public void testElementSearchMethods() {
        open("https://slqamsk.github.io/demo/search-demo/");

        By myLocator = By.id("submit-button");
        SelenideElement myElement = Selenide.element(myLocator);
        myElement.shouldBe(visible);
        myElement.shouldBe(Condition.visible);
        element(By.id("submit-button")).shouldBe(visible);
        $(By.id("submit-button")).shouldBe(visible);

        $(By.name("interests")).shouldBe(visible);
        $(By.className("nav-link")).shouldBe(visible);
        $(By.tagName("input")).shouldBe(visible);
        $(By.linkText("Контакты и обратная связь")).shouldBe(visible);
        $(By.partialLinkText("длинный")).shouldBe(visible);
    }
}