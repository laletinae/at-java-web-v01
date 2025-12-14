package demo.part02;
import org.junit.jupiter.api.Test;
import com.codeborne.selenide.Configuration;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
public class BrowserPropertiesTests {
    @Test
    void test_browser_properties() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1500x500";
        Configuration.browserPosition = "100x200";
        open("https://ya.ru");
        sleep(10_000);
        getWebDriver().manage().window().maximize();
        sleep(10_000);
    }
}