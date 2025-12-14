package work.part02;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/**
 * Домашнее задание на 13 декабря: Поиск элементов по xPath, без использования осей
 * Поиск курсов на сайте Специалист
 * Перестало работать открытие сайта Специалист (любой браузер)
 * ERR_CONNECTION_TIMED_OUT
 * Connection reset
 */
public class SearchCoursesSpecialistTests {

    //Тестируемая страница
    static String pageUrl = "https://www.specialist.ru/";
    static WebDriver driver;


    /**
     * Открытие главной страницы сайта
     */
    @BeforeAll
    static void openPage(TestInfo test_info) {
        Configuration.browser = "firefox";

        //Увеличение времени ожидания открытия страницы (не помогло)
        //driver = getWebDriver();
        //driver.manage().timeouts().pageLoadTimeout(160, java.util.concurrent.TimeUnit.SECONDS); //этот способ не использовать

        //Неявные ожидания (лучше использовать явные ожидания)
        Configuration.pageLoadTimeout = 30_000; //время загрузки страницы - использовать этот метод
        Configuration.pageLoadStrategy = "eager"; //жадная загрузка (не дожидаясь подгрузки всех ресурсов)

        //лучше использовать явные ожидания, слайд 55 - попробовать
        // $("#dynamic_content").shouldBe(exist, Duration.ofSeconds(6));

        open(pageUrl);





        System.out.println("Страница " + pageUrl + "открыта");
    }

    /**
     * Неверно прояитанное задание из домешки (всплывающий поиск всмето каталога курсов)
     * Поиск на странице курса по тестированию и его даты начала (без использования осей в xPath)
     * На странице воспользоваться поиском по слову "тестирование"
     * Найти дату начала курса "Автоматизированное тестирование веб-приложений с использованием Selenium"
     *
     */
    @Test
    @DisplayName("Поиск на странице курса по тестированию и его даты начала (без использования осей в xPath)")
    public void test01SearchCourseDateBeginSpecialist() {


        //кнопка для открытия строки поиска
        SelenideElement $btnSearch = $x("//button[contains(@class,'js-header-search-button')]").shouldBe(visible);
        $btnSearch.click();

        //Поле поиска
        SelenideElement $fieldSearch = $x("//input[@placeholder='Поиск по сайту']").shouldBe(visible);
        $fieldSearch.setValue("Автоматизированное тестирование веб-приложений с использованием Selenium");

        //Кнопка искать
        SelenideElement $btnFind = $x("//div[@class='header-menu-search-body']/form/button").shouldBe(visible);
        //
        // getWebDriver().execute_script("arguments[0].click();", element)
        // 1. Cast the driver to JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 2. Locate the element using standard Selenium methods
        //WebElement button = driver.findElement(By.id("loginButtonId"));

        // 3. Execute the JavaScript click operation
        // arguments[0] refers to the first argument passed after the script string (which is 'button' here)
        js.executeScript("arguments[0].click();", $btnFind);

        //$btnSearch.click();


        sleep(1000);
    }

    /**
     * Неверно прояитанное задание из домешки (всплывающий поиск всмето каталога курсов)
     * Поиск на странице курса по тестированию и его даты начала (без использования осей в xPath)
     * На странице воспользоваться поиском по слову "тестирование"
     * Найти дату начала курса "Автоматизированное тестирование веб-приложений с использованием Selenium"
     *
     */
    @Test
    @DisplayName("Поиск на странице курса по тестированию В КАТАЛОГЕ КУРСОВ и его даты начала (без использования осей в xPath)")
    public void test02SearchCourseCatalogueDateBeginSpecialist() {
        //Переход в каталог курсов
        //SelenideElement $menuCourse = $x("//div[@id='js-mobile-menu']/a[contains(.,'Курсы')]").shouldBe(visible);
        //SelenideElement $menuCourse = $x("//div[@id='js-mobile-menu']/section/a").shouldBe(visible);
        //SelenideElement $menuCourse = $x("//div/a").shouldBe(visible);


        SelenideElement $menuCourse = $x("//div[@id='js-mobile-menu']/section[3]/div/div/nav/ul/li[1]/a").shouldBe(visible);

        ////*[@id="js-mobile-menu"]/section[3]/div/div/nav/ul/li[1]/a/text()
        ////*[@id="js-mobile-menu"]/section[3]/div/div/nav/ul/li[1]/a


        //SelenideElement $menuCourse = $x("//a[contains(text(),'Курсы')]").shouldBe(visible);
        $menuCourse.click();

        //SelenideElement $subMenuCourse = $x("/html/body/header/div/section[3]/div/div/nav/ul/li[1]/ul/li[1]/a").shouldBe(visible);
        // //*[@id="js-mobile-menu"]/section[3]/div/div/nav/ul/li[1]/ul/li[1]/a
        //SelenideElement $subMenuCourse = $x("//a[contains(text(),'Каталог курсов')]").shouldBe(visible);
        SelenideElement $subMenuCourse = $x("//*[@id='js-mobile-menu']/section[3]/div/div/nav/ul/li[1]/ul/li[1]/a").shouldBe(visible);

        $subMenuCourse.click();

        //Поле ввода для поиска
        SelenideElement fieldSearch = $x("//input[@id='CourseName']");
        fieldSearch.type("тестирование");

        //Куки закрыть, т.к. не видна кнопка поиска
        $("#closeCookieConsent").click();

        //Кнопка поиска
        SelenideElement buttonSearch = $x("/html/body/section[1]/div/div/div/form/div[4]/div[1]/p/button");
        buttonSearch.click();


        ///html/body/div[5]/div[2]/div/div/div[1]/div/section[2]/article[8]/section[2]/dl/dd/a

        //Искомый курс
        SelenideElement courseName = $x("//a[contains(.,'Автоматизированное тестирование веб-приложений с использованием Selenium')]");
        courseName.should(exist);





        sleep(3_000);
    }

}
