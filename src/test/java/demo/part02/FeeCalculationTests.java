package demo.part02;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
public class FeeCalculationTests {
    @Test
    public void test01() {
        open("https://slqa.ru/cases/fc/v01/");
        SelenideElement myElement = $("input[name=sum]");

        myElement.sendKeys("100");
        $(By.name("submit")).click();

        myElement.sendKeys("2000");
        $(By.name("submit")).click();
    }
}