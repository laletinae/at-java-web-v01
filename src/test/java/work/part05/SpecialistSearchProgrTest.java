package work.part05;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;

/*
 Домашнее задание на 21 декабря, обязательная часть
 AT-1 (обязательное):
 На сайте https://www.specialist.ru/ в модальном окне "Наш сайт использует файлы cookie" нажать "Согласен".
 Выбрать пункт меню "Форматы обучения", затем "Свободное обучение", затем "Выбрать курс",
 в поле "Направление" выбрать "Программирование", нажать кнопку "Применить" и
 убедиться, что на странице есть элемент содержащий текст "Тестирование ПО".
 */
public class SpecialistSearchProgrTest {
    @BeforeAll
    static void beforeAll(TestInfo test_info) {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        open("https://www.specialist.ru/");
        getWebDriver().manage().window().maximize();
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @Test
    @Step("Поиск форматов обучения")
    void searchCourse() {
        //Принять cookie
        $("#cookieConsent__ok").click();

        //Пункт верхнего меню
        step("Выбираем пункт верхнего меню форматы обучения", () ->
        {
            SelenideElement $menuFormat = $x("//div[@id='js-mobile-menu']//a[contains(text(),'Форматы обучения')]");
            $menuFormat.click();
        }
        );

        //Формат "свободное обучение"
        step("Выбираем формат обучения свободное ПО", () ->
        {
        SelenideElement $linkSvobodnoe = $x("//article[@class='format-article']//a[contains(text(),'Свободное обучение')]");
        $linkSvobodnoe.click();
        }
        );

        //Кнопка "Выбрать курс"
        SelenideElement $buttonChoose = $x("//a[@href='#selectCourse']");
        $buttonChoose.click();

        //В поле "Направление" выбрать "Программирование"
        SelenideElement $selectNapravl = $x("//select[@id='Filter_CategoriesDirectionFilter']");
        $selectNapravl.selectOption("Программирование");

        // нажать кнопку "Применить"
        SelenideElement $buttonPrimen = $x("//button[@id='sendBtn']");
        $buttonPrimen.click();

        // Убедиться, что на странице есть элемент содержащий текст "Тестирование ПО"
        // 2 варианта поиска (оба работают одинаково)

        // Вариант поиска 1 (поиск по классу тега <section> и по тексту тега <a>)
        SelenideElement $testirPo1 = $x("//section[@class='schedule-course-block']//a[contains(text(),'Тестирование ПО')]");
        $testirPo1.should(exist);

        // Вариант 2 (поиск по классу и тексту тега <a> с использованием and между условиями)
        SelenideElement $testirPo2 = $x("//a[@class='course-link' and contains(text(),'Тестирование ПО')]");
        $testirPo2.should(exist);

        sleep (500);
    }
}
