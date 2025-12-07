package work.part02;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class FreeCalculationTests {
    @Test
    public void testElementSearchMethods() {
        open("https://slqa.ru/cases/fc/v01/");

        sleep(3000);

        //By byBtnSum = By.id("sum");
        SelenideElement fieldSum = Selenide.element(By.name("sum"));
        SelenideElement btnCount = Selenide.element(By.name("submit"));

        fieldSum.sendKeys("1234");
        btnCount.click();

        sleep(3000);


        fieldSum.shouldBe(Condition.enabled).clear();
        //fieldSum.clear();

        fieldSum.type("654321");
        btnCount.click();


        sleep(3000);
    }
}