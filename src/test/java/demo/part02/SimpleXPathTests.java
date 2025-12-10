package demo.part02;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;

public class SimpleXPathTests {
    @BeforeEach
    void setUp() {
        open("https://slqamsk.github.io/tmp/xPath01.html");
    }

    @Test
    void testPageH1() {
        //Содержит такой текст
        $x("//h1").shouldHave(text("Учебная страница для XPath"));
        //Точное соответствие
        $x("//h1").shouldHave(exactText("Учебная страница для XPath"));
    }

    @Test
    void testSpecialParagraph() {
        $x("//p[@class='special-paragraph']").shouldHave(exactText("Этот параграф особенный - он единственный на странице с таким классом."));
    }

    @Test
    void testFindByTexts() {
        open("https://slqa.ru/cases/xPathSimpleForm/");
        $x("//*[starts-with(.,'Казахстан')]").shouldHave(text("площадь 2 724 902"));
    }
}
