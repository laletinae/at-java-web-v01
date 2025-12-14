package demo.part05;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;


public class SLFlightsTests {
    @ParameterizedTest
    @CsvFileSource (resources = "sl_data.csv", numLinesToSkip = 2)
    void test01(String login, String pass, String fio) {
        open("https://slqamsk.github.io/cases/slflights/v01/");
        $("#username").sendKeys(login);
        $("#password").sendKeys(pass);
        $("#loginButton").click();
        $("#logoutButton").shouldBe(visible);
        $("#greeting").shouldHave(exactText("Добро пожаловать, " + fio + "!"));
        //$("#greeting").shouldHave(text(fio));
    }
}