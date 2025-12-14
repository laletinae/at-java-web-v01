package demo.part04;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import java.net.HttpURLConnection;
import java.net.URL;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
public class AllLinksTests {
    @Test
    void test01AllLinksV1() {
        //open("https://ru.wikipedia.org/wiki/Selenium");
        open("https://slqa.ru/cases/");
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        ElementsCollection ec = $$("a").filter(clickable).filter(visible).filter(enabled);
        System.out.println(ec.size());
        for (int i = 0; i < ec.size(); i++) {
            System.out.println(i + ". " + ec.get(i).getText() + ", " + ec.get(i).getDomAttribute("href")
                    + ", размер: " + ec.get(i).getSize());
            if ((ec.get(i).getSize().height <= 1) || (ec.get(i).getSize().width <= 1)) {
                System.out.println("Элемент слишком маленький, по нему невозможно кликнуть.");
                continue;
            }
            actions.keyDown(Keys.LEFT_CONTROL).build().perform();
            ec.get(i).click();
            switchTo().window(1);
            $("body").shouldBe(exist);
            String str_url = WebDriverRunner.driver().url();
            try {
                URL url = new URL(str_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                int code = connection.getResponseCode();
                System.out.println(str_url + " - " + code);
            } catch (Exception e) {
                System.out.println(str_url + " - некорректная ссылка");
                //throw new RuntimeException(e);
            }
            closeWindow();
            switchTo().window(0);
        }
    }

    @Test
    void test02AllLinksV2() {
        open("https://ru.wikipedia.org/wiki/Selenium");
        //open("https://slqa.ru/cases/");
        Actions actions = new Actions(WebDriverRunner.getWebDriver());
        int i = 0;
        for (SelenideElement se : $$("a").filter(clickable).filter(visible).filter(enabled)) {
            i++;
            System.out.println(i + ". " + se.getText() + ", " + se.getDomAttribute("href")
                    + ", размер: " + se.getSize());
            if ((se.getSize().height <= 1) || (se.getSize().width <= 1)) {
                System.out.println("Элемент слишком маленький, по нему невозможно кликнуть.");
                continue;
            }
            actions.keyDown(Keys.LEFT_CONTROL).build().perform();
            se.click();
            switchTo().window(1);
            $("body").shouldBe(exist);
            String str_url = WebDriverRunner.driver().url();
            try {
                URL url = new URL(str_url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36");
                int code = connection.getResponseCode();
                System.out.println(str_url + " - " + code);
            } catch (Exception e) {
                System.out.println(str_url + " - некорректная ссылка");
            }
            closeWindow();
            switchTo().window(0);
        }
    }
}