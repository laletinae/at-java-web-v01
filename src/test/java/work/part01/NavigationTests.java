package work.part01;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class NavigationTests {
    @Test
    void testNavigation() {
        Configuration.browser = "firefox";

        open("https://google.com");
        sleep(3000);

        open("https://yandex.ru");
        sleep(3000);

        open("https://www.specialist.ru/");
        sleep(3000);

        back();
        sleep(3000);
        back();
        sleep(3000);
        forward();
        sleep(3000);


    }
}
