package work.part02;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/*
 * Простой поиск элементов по тексту использованием xPath
 * contains
 * использование атрибутов в xPath
 * Условия в Selenide, который можно применить к элементам на странице: shouldHave(exactText()) и shouldHave(text())
 * Находим несколько тегов с одинаковым классом на странице, понимаем что у них есть порядковые номера
 */
public class SimpleXPathTests {
    @BeforeEach
    void openPage () {
        open("https://slqamsk.github.io/tmp/xPath01.html");
    }

    @Test
    void testXpathFinder1() {
        //Варианты как найти элемент на странице используя xPath
        //Пример с использованием атрибута for тега label и с использованием поиска по тексту с помощью предиката contains
        //У найденного элемента проверяем совпадение с искомым текстом
        //Вариант 1 и 2 - разные варианты написания одного и того же
        //Вариант 3 и 4 - поиск в xPath по совпадению с текстом
        //После того как нашли проверяем по сути тоже самое условием shouldHave(text())

        //Строка текста для поиска
        var textToSearch = "Вегетарианская - 500 ₽ (томатный соус, моцарелла, перец, грибы, оливки, кукуруза)";

        //Вариант 1: с объявлением объекта и lazy initialization
        SelenideElement $header = $x("//label[@for='pizza5']");
        $header.shouldHave(text(textToSearch));

        //Вариант 2: в одну строку
        $x("//label[@for='pizza5']").shouldHave(text(textToSearch));

        //Варивант 3 поиск по тексту с использованием contains и .
        $x("//label[contains(.,'" + textToSearch + "')]").shouldHave(exactText(textToSearch));

        //Варивант 4 поиск по тексту с использованием contains и text()
        $x("//label[contains(text(),'" + textToSearch + "')]").shouldHave(exactText(textToSearch));

        sleep(500);

    }


    /**
     * Проверка найденного элемента на соответсвие условиям (совпадение с текстом)
     * Разница в использовании text и exactText
     *
     */
    @Test
    void testPageH1() {

        //Строка текста для поиска полного совпадения
        var textToSearchFull = "Учебная страница для XPath";

        //Строка текста для поиска частичного совпадения
        var textToSearchPartial = "Учебная страница для";


        //Вариант 1 - поиск по тексту shouldHave(text()) - частичное совпадение
        $x("//h1").shouldHave(text(textToSearchPartial));

        //Варивант 2 поиск по тексту exactText(text()) - полное совпадение
        $x("//h1").shouldHave(exactText(textToSearchFull));


    }

    /**
     * Задание: Найти параграф с классом special-paragraph через относительный XPath.
     * Проверить, что его текст равен: "Этот параграф особенный - он единственный на странице с таким классом."
     * =========================================================================================
     * Если на странице будет найдено несколько элементов (например, с одинаковым классом)
     * у каждого элемента будет свой порядковый номер
     *
     */
    @Test
    void testSpecialParagraph() {
        $x("//p[@class='special-paragraph']").shouldHave(text("Этот параграф особенный - он единственный на странице с таким классом."));
    }

    /**
     * Задание: Найти все три параграфа с классом info-text через XPath, проверить, что они содержат соотв. тексты
     * =========================================================================================
     * Если на странице будет найдено несколько элементов (например, с одинаковым классом)
     * у каждого элемента будет свой порядковый номер
     *
     */
    @Test
    void testInfoParagraph() {
        $x("//p[@class='info-text'][1]").shouldHave(text("Это первый информационный текст."));
        $x("//p[@class='info-text'][2]").shouldHave(text("Это второй информационный текст."));
        $x("//p[@class='info-text'][3]").shouldHave(text("Это третий информационный текст."));
    }

    /**
     * Задание: Найти обе ссылки (тег <a>) с классом external-link через XPath.
     * Проверить, что: 1) Первая ссылка содержит текст "Внешняя ссылка (Example)".
     * 2) Вторая ссылка содержит текст "Внешняя ссылка (Google)".
     * =========================================================================================
     * Если на странице будет найдено несколько элементов (например, с одинаковым классом)
     * у каждого элемента будет свой порядковый номер
     *
     */
    @Test
    void testExternalLinks() {
        $x("//a[@class='external-link'][1]").shouldHave(text("Внешняя ссылка (Example)"));
        $x("//a[@class='external-link'][2]").shouldHave(text("Внешняя ссылка (Google)"));

    }
}
