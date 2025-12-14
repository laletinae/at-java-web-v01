package demo.part04;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import static com.codeborne.selenide.Selenide.*;
public class DropDownTests {
    @Test
    void test01DropDown() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/form-fields/");
        sleep(2_000);
        $("#automation").selectOption(1);
        sleep(2_000);
        $("#automation").selectOption(0);
        sleep(2_000);
        $("#automation").selectOption("Undecided");
        sleep(2_000);
        $("#automation").selectOptionByValue("no");
        sleep(2_000);
    }
}