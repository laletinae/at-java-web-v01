package work.part02;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

/*
* Поиск элементов по тексту (использование библиотеки Selenide и xPath)
* Selenide: byText, withText
* xPath: contains, starts-with
* Разница между . и text()
*/
public class SimpleTextElementSearchTests {
    private static final Logger log = LoggerFactory.getLogger(SimpleTextElementSearchTests.class);

    @BeforeEach
    void openPage () {
        open("https://slqa.ru/cases/xPathSimpleForm/");
    }


    /**
     * Задание: На странице найти элемент содержащий текст "Москва" с помощью команды библиотеки Selenide
     * Проверить, что этот элемент содержит текст "250 единиц"
     * ======================================================================
     * Варианты поиска по точному совпадению (или части) при помощи Selenide
     * byText - поиск точного совпадения текста
     * withText - поиск совпадения по части текста
     */
    @Test
    void test01Moscow() {

        //Найти элемент с текстом Москва
        //SelenideElement $moscow = $(byText("Москва"));
        SelenideElement $moscow = $(withText("Москва"));

        //проверить что он содержит 250 единиц
        $moscow.shouldHave(text("250 единиц"));
    }

    /**
     * Задание: На странице найти элемент, содержащий текст "Питер" с помощью xPath
     * Проверить, что этот элемент содержит текст "180 единиц".
     * ======================================================================
     * Варианты поиска по точному совпадению (или части) при помощи xPath
     * //*[.='Текст']  и //*[text()='Текст'] поиск по точному тексту элемента
     * //*[contains(.,'Текст')]  и //*[contains(text(),'Текст')] поиск по тексту содержащемуся внутри элемента
     * Разница между . и text() в том, что:
     * . - вернёт текст, вырезав в нём теги типа <span>
     * text() - вернёт вместе с внутренними тегами и совпадения может не быть
     *
     */
    @Test
    void test02Piter() {

        //Найти элемент с текстом Питер
        SelenideElement $piter = $x("//*[contains(text(),'Питер')]");

        //проверить что он содержит 180 единиц
        $piter.shouldHave(text("180 единиц"));
    }

    /**
     * Задание: На странице найти элемент, начинающийся с текста "Казахстан"
     * Проверить, что он содержит текст "площадь 2 724 902".
     * ======================================================================
     * Варианты поиска "начинается с" есть только в xPath (нет в SCC, Selenide)
     * //*[starts-with(.,'Текст')]  и //*[starts-with(text(),'Текст')] поиск по тексту с которого начинается элемент
     * Разница между . и text() в том, что:
     * . - вернёт текст, вырезав в нём теги типа <span>
     * text() - вернёт вместе с внутренними тегами и совпадения может не быть
     *
     */
    @Test
    void test03Kazakhstan() {

        //Найти элемент начинающийся с Казахстан
        SelenideElement $kazakhstan = $x("//*[starts-with(text(),'Казахстан')]");

        //проверить что он содержит текст площадь 2 724 902
        $kazakhstan.shouldHave(text("площадь 2 724 902"));
    }
}
