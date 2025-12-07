package demo.part02;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
public class NavigationTests {
    @Test
    void testNavigation() {
        open("https://ya.ru/");
        sleep(10_000);
        open("https://ru.wikipedia.org");
        sleep(3_000);
        open("https://ru.wikipedia.org/wiki/Selenium");
        sleep(3_000);
        back();
        sleep(3_000);
        back();
        sleep(3_000);
        forward();
        sleep(10_000);
    }
}