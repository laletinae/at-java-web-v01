package demo.part03;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

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
        //Вариант 3 и 4 - поиск в xPath по совпадению с текстом (не так работает?)

        //Строка текста для поиска
        var textToSearch = "Вегетарианская - 500 ₽ (томатный соус, моцарелла, перец, грибы, оливки, кукуруза)";

        //Вариант 1: с объявлением объекта и lazy initialization
        SelenideElement $header = $x("//label[@for='pizza5']");
        $header.shouldHave(text(textToSearch));

        //Вариант 2: в одну строку
        $x("//label[@for='pizza5']").shouldHave(text(textToSearch));

        //Варивант 3 поиск по тексту с использованием contains и .
        //Не работает: возвращает первый найденный label на странице
        //А при использовании //*[contains(.,textToSearch)] возвращает всю страницу целиком
        $x("//label[contains(.,'" + textToSearch + "')]").shouldHave(exactText(textToSearch));

        //Варивант 4 поиск по тексту с использованием contains и text()
        //Не работает (аналогично варианту 3)
        $x("//label[contains(text(),'" + textToSearch + "')]").shouldHave(exactText(textToSearch));

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

    @Test
    void testSpecialParagraph() {
        $x("//p[@class='special-paragraph']").shouldHave(text("Этот параграф особенный - он единственный на странице с таким классом."));
    }

    @Test
    void testInfoParagraph() {
        $x("//p[@class='info-text'][1]").shouldHave(text("Это первый информационный текст."));
    }
}
