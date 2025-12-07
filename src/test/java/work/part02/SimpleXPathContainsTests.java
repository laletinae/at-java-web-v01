package work.part02;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class SimpleXPathContainsTests {
    @BeforeEach
    void openPage () {
        open("https://slqa.ru/cases/xPathSimpleForm/");
    }


    @Test
    void testMoscow() {

        SelenideElement $moscow = $(withText("Москва"));
        $moscow.shouldHave(text("250 единиц"));
    }

}
