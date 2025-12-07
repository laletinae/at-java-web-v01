package work.part02;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class SimpleXPathTests {
    @Test
    void testPageH1() {
        open("https://slqamsk.github.io/tmp/xPath01.html");

        //Варивант 1
        SelenideElement $header = $x("//h1");
        $header.shouldHave(text("Учебная страница для XPath"));

        //Варивант 2
        $x("//h1").shouldHave(text("Учебная страница для XPath"));


    }
}
