package work.part05;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/**
 * Пример работы с параметризованным тестом для тестирования формы авторизации
 * Использование аннотаций ParameterizedTest, CsvSource
 * и BeforeAll, BeforeEach, AfterEach
 *
 */
public class ComissionParameterizedTests {

    //Поиск элементов по CSS-селектору
    SelenideElement summa = $x("//input[@name='sum']");
    SelenideElement button = $x("//input[@name='submit']");
    SelenideElement comission = $x("//span[@name='com']");

    static String pageUrl = "https://slqa.ru/cases/fc/v01/";
    /**
     * Пример запуска кода перед всеми тестами (например, выбор браузера и открытие страницы)
     *
     */
    @BeforeAll
    static void setup() {
        Configuration.browser = "firefox";
        System.out.println("Браузер " + Configuration.browser);
    }

    /**
     * Пример запуска кода перед каждым тестом (например, написать название теста, который начал выполнение)
     *
     */
    @BeforeEach
    void printTestNameBegin(TestInfo test_info) {
        System.out.println("Начало " + test_info.getDisplayName());
        open(pageUrl);
        System.out.println("Страница " + pageUrl);
    }

    /**
     * Пример запуска кода после каждого теста (например, написать название теста, который закончил выполнение)
     *
     */
    @AfterEach
    void printTestNameEnd(TestInfo test_info) {

        closeWindow();
        System.out.println("Конец " + test_info.getDisplayName());
    }


    /**
     * Задание:
     * Для формы расчёта комиссии https://slqa.ru/cases/fc/v01/
     * сделайте параметризованный тест, который ничего не будет проверять и передаст значения:
     * 100
     * 2000
     * "йцукен"
     * ============================================================
     * #{index}  - порядковый номер итерации
     * {0} значение 1-го (нумерация с нуля) параметра
     * В тесте с ValueSource только 1 параметр
     *
     */
    @ParameterizedTest (name = "Расчёт комиссии, #{index}, параметр: {0}")
    @ValueSource (strings = {"100","2000","йцукен"})

    public void test01Comission(String summValue) {
        summa.setValue(summValue);
        button.click();

        sleep(500);

    }

    /**
     * Задание:
     * Для формы расчёта комиссии https://slqa.ru/cases/fc/v01/
     * сделайте параметризованный тест, который работает с csv файлом в качестве источника данных
     * и проверяет, верно ли рассчитана комиссия
     * ============================================================
     * numLinesToSkip - Начиная с какой строки (пропускаем названия столбцов)
     * {0} значение 1-го (нумерация с нуля) параметра
     * {1} значение 2-го (нумерация с нуля) параметра
     * В тесте с CsvFileSource и CsvSource это номер столбца с данными
     */
    @ParameterizedTest (name = "Расчёт комиссии, #{index}, сумма: {0} комиссия: {1}")
    @CsvFileSource(resources = "com_test.csv", numLinesToSkip = 1)

    public void test02Comission(String summValue, String commValue) {
        summa.setValue(summValue);
        button.click();

        //Проверим сумму комиссии
        comission.shouldBe(text(commValue));

        System.out.println("Сумма = " + summValue + ", комиссия = " + commValue);


        sleep(500);

    }

}
