package work.part04;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WindowType;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.closeWindow;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AllLinksTests {
    @Test
    void test01Habr() {
        Configuration.pageLoadStrategy = "eager";
        open("https://habr.com/ru/articles/");

        ElementsCollection elements = $$("a");

        for (SelenideElement element : elements.filter(visible)) {
            //Проверка состояния без assertion, возвращает булево значение (можно проверить в условии)
            element.isEnabled();
            element.isDisplayed();
            element.exists();
            System.out.println(element.getText());
            System.out.println(element.getAttribute("href"));

            /*if (element.shouldBe(visible).shouldBe(clickable)){

            }
            if ($visibleLink) {
                System.out.println(element.getText());
                System.out.println(element.getAttribute("href"));
            }*/


        }


        //sleep(2_000);
        /*
        switchTo().newWindow(WindowType.WINDOW);
        open("https://demoqa.com/");
        getWebDriver().manage().window().setSize(new Dimension(1000, 1000));
        getWebDriver().manage().window().setPosition(new Point(1020, 0));
        sleep(2_000);
        switchTo().window(0);
        $x("//a[text()='Popups']").click();
        sleep(2_000);
        switchTo().window(1);
        $x("//h5[text()='Elements']/parent::* /parent::*").click();
        sleep(2_000);
        closeWindow();
        sleep(2_000);
        switchTo().window(0);
        closeWindow();
        sleep(2_000);
        */
    }
}
