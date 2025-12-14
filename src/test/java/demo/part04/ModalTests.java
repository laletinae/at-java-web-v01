package demo.part04;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Selenide.*;
public class ModalTests {
    @Test
    void test01SimpleModal() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/modals/");
        $x("//div[@id='popmake-1318']").shouldBe(exist);
        $("#simpleModal").click();
        sleep(2_000);
        $x("//a[text()='Home']").click();
        $x("//div[@id='popmake-1318']").shouldBe(visible);
        $x("//div[@id='popmake-1318']//button").shouldBe(clickable);
        $x("//div[@id='popmake-1318']//button").click();
        sleep(2_000);
    }
}