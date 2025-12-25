package work.part07;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import work.part07.aviaSalesPages.searchMainPage;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AviaSalesTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("https://www.aviasales.ru/");
        getWebDriver().manage().window().maximize();
    }

    // ... Автотесты
    // 1. Неуспешный логин
    @Test
    void test01SearchMoscow() {
        searchMainPage searchMainPage = new searchMainPage();
        searchMainPage.search("Москва", "Сочи");
        searchMainPage.isFound();

        sleep(10000);
    }
}
