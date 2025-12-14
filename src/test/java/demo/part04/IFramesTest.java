package demo.part04;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;


import java.time.Duration;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class IFramesTest {
    @Test
    void test01IFrame() {
        //Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";

        open("https://practice-automation.com/iframes/");
        sleep(3_000);
        getWebDriver().manage().window().maximize();

        //$x("//*[@class='wp-block-spacer'][2]").scrollTo(); //для демонстрации
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

    @Test
    void test02IFrame() {
        Configuration.pageLoadStrategy = "eager";
        open("https://demoqa.com/frames");
        sleep(3000);
        getWebDriver().manage().window().maximize();


        switchTo().frame($x("//iframe[@id='frame1']"));
        $("#sampleHeading").shouldHave(text("This is a sample page"));
        $x("//h1[contains(.,'This is a sample page')]").shouldBe(exist, Duration.ofSeconds(10));

        switchTo().defaultContent();
        $x("//header/a").click();
        sleep(3000);
    }
}