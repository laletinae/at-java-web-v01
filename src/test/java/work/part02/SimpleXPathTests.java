package work.part02;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class SimpleXPathTests {
    @Test
    void testPageH1() {
        open("https://slqamsk.github.io/cases/slflights/v01/");

        $x("//h1");
    }
}
