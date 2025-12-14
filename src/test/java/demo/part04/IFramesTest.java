package demo.part04;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class IFramesTest {
    @Test
    void test01IFrame() {
        //Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";

        open("https://practice-automation.com/iframes/");
        getWebDriver().manage().window().maximize();

        $x("//*[@class='wp-block-spacer'][2]").scrollTo(); //для демонстрации
        sleep(5_000);
        switchTo().frame($x("//div[@class='entry-content']/iframe[@id='iframe-2']"));
        $x("//a[contains(.,'About')]").click();
        sleep(5_000);
        $x("//a[contains(.,'About Selenium')]").click();
        sleep(5_000);

        switchTo().defaultContent();
        $x("//body").scrollTo(); //для демонстрации
        sleep(5_000);
        $x("//a[text()='Home']").click();
        sleep(10_000);
    }
}