package demo.part04;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
public class AlertsTests {
    @Test
    void test01SimpleAlert() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#alert").click();
        sleep(2_000);
        switchTo()
                .alert()
                .accept();
        sleep(2_000);
        switchTo().defaultContent();
        $x("//a[text()='Home']").click();
    }
    @Test
    void test02ConfirmOk() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#confirm").click();
        Alert alert = switchTo().alert();
        System.out.println(alert.getText());
        sleep(2_000);
        alert.accept();
        sleep(2_000);
    }
    @Test
    void test03ConfirmCancel() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#confirm").click();
        Alert alert = switchTo().alert();
        System.out.println(alert.getText());
        sleep(2_000);
        alert.dismiss();
        sleep(2_000);
    }
    @Test
    void test04PromptOk() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#prompt").click();
        Alert alert = switchTo().alert();
        System.out.println(alert.getText());
        alert.sendKeys("Сергей");
        sleep(2_000);
        alert.accept();
        sleep(2_000);
    }
    @Test
    void test05PromptCancel() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#prompt").click();
        Alert alert = switchTo().alert();
        System.out.println(alert.getText());
        sleep(2_000);
        alert.dismiss();
        sleep(2_000);
    }
}

