package work.part04;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

public class AlertsTests {
    @Test
    void test01SimpleAlert() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#alert").click();
        sleep(2_000);
        //Рекомендованный стиль, без создания переменной и т.д.
        switchTo()
                .alert()
                .accept();
        //По логике, из алерта возвращается сам. Но по правила хорошего тона - нужно переключиться обратно
        switchTo().defaultContent();
        sleep(2_000);
    }
    @Test
    void test02ConfirmOk() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/popups/");
        $("#confirm").click();

        //НЕРекомендованный стиль, с созданием переменной и т.д.
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
    @Test
    void test06SimpleAlertAccept() {
        Configuration.pageLoadStrategy = "eager";
        open("https://demoqa.com/alerts");
        $("#alertButton").click();
        switchTo().alert().accept();
        //System.out.println(alert.getText());
        //sleep(2_000);
        //alert.dismiss();
        sleep(2_000);
    }
    @Test
    @Tag("SmokeTest")
    @Tag("SecondTag")
    void test07PromptAlertCancel() {
        Configuration.pageLoadStrategy = "eager";
        open("https://demoqa.com/alerts");
        $("#alertButton").click();
        switchTo().alert().accept();
        //System.out.println(alert.getText());
        //sleep(2_000);
        //alert.dismiss();
        sleep(2_000);
    }
}
