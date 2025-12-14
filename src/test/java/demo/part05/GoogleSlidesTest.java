import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;

import java.time.Duration;

public class GoogleSlidesTest {
    @Test
    void test01ChangeGoogleSlids() {
        Configuration.pageLoadTimeout = 60_000;
        Configuration.pageLoadStrategy = "eager";
        open("https://docs.google.com/presentation/d/11DCngs_fuMljtNguzYw5qrFxoaz6C7pu0Nhq8Tv8R58");
        getWebDriver().manage().window().maximize();
        sleep(30_000);
        $("#punch-start-presentation-left").shouldBe(clickable, Duration.ofSeconds(30)).click();
        sleep(30_000);

        Actions actions = new Actions(getWebDriver());

        // Перейти к первому слайду (опционально, для надёжности)
        actions.sendKeys(Keys.HOME).perform();
        sleep(2_000);

        // Бесконечный цикл: вперёд-назад каждые 10 секунд
        while (true) {
            getWebDriver().getTitle();
            actions.sendKeys(Keys.RIGHT).perform();
            sleep(10_000);

            actions.sendKeys(Keys.LEFT).perform();
            sleep(10_000);
        }

    }
}
