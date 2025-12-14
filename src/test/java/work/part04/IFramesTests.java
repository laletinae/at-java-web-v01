package work.part04;


import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class IFramesTests {
    @Test
    void test01IFrame() {
        //Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";

        open("https://practice-automation.com/iframes/");
        getWebDriver().manage().window().maximize();

        //прокрутка страницы до следующего после последнего iframe элемента (для демонстрации)
        //не работает на маленьком разрешении экрана
        $x("//*[@class='wp-block-spacer'][2]").scrollTo();
        sleep(5_000);
        switchTo().frame($x("//div[@class='entry-content']/iframe[@id='iframe-2']"));
        $x("//a[contains(.,'About')]").click(); //не кликабелен на мелком разрешении, проверить дома!!!
        sleep(5_000);
        $x("//a[contains(.,'About Selenium')]").click();
        sleep(5_000);

        switchTo().defaultContent();
        $x("//body").scrollTo(); //для демонстрации
        sleep(5_000);
        $x("//a[text()='Home']").click();
        sleep(10_000);
    }
    @Test
    void test02IFrame() {
        //Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";

        open("https://demoqa.com/frames");
        getWebDriver().manage().window().maximize();


        switchTo().frame($x("//iframe[@id='frame1']"));

        $x("//h1[@id='sampleHeading']").shouldHave(text("This is a sample page"));

        sleep(1000);
    }
}
