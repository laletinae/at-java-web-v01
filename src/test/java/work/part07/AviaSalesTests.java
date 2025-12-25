package work.part07;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import work.part07.aviaSalesPages.searchMainPage;
import work.part07.aviaSalesPages.searchResults;


import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

@TestMethodOrder(MethodOrderer.DisplayName.class)
public class AviaSalesTests {
    @BeforeAll
    static void beforeAll() {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "chrome";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    void setUp() {
        open("https://www.aviasales.ru/");
        getWebDriver().manage().window().maximize();
    }

    // ... Автотесты
    // 1. Проверка поиск билетов Москва-Сочи на 12-01-2026
    @Test
    void test01SearchMoscowSochi12012026() {
        searchMainPage searchMainPage = new searchMainPage();
        searchMainPage.search("Москва", "Сочи", "2026-01-12");
        //searchMainPage.isFound();

        searchResults searchResults = new searchResults();
        sleep(10000);
        searchResults.checkDirectTicketsExist();
        searchResults.allFlightsPreviewsPrint();

        sleep(10000);
    }
}
