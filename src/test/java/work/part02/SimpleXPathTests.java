package work.part02;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SimpleXPathTests {
    @BeforeEach
    void openPage () {
        open("https://slqamsk.github.io/tmp/xPath01.html");
    }


    @Test
    void testPageH1() {

        //Варивант 1
        SelenideElement $header = $x("//h1");
        $header.shouldHave(text("Учебная страница для XPath"));

        //Варивант 2
        $x("//h1").shouldHave(exactText("Учебная страница для XPath"));
    }

    @Test
    void testSpecialParagraph() {
        $x("//p[@class='special-paragraph']").shouldHave(text("Этот параграф особенный - он единственный на странице с таким классом."));
    }

    @Test
    void testInfoParagraph() {
        $x("//p[@class='info-text'][1]").shouldHave(text("Это первый информационный текст."));
    }
}
