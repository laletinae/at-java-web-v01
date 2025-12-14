package work.part03;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

/*
 * Оси в xPath
 */
public class PizzaCartTests {
    @BeforeAll
    static void openPage () {
        open("https://slqamsk.github.io/cases/pizza/v08/");
    }


    /*
     * написать автотест, который добавит пиццы "Маргарита" и "Четыре сыра" в корзину, не пользуясь атрибутом data-id.
     */
    @Test
    void testXpathFinder1() {
        //


        //Вариант 1: с использованием ключевых слов
        SelenideElement $margarita = $x("//h3[contains(.,'Маргарита')]/ancestor::div/descendant::button");

        //Вариант 2: с короткими путями
        // .. - наверх (предок)
        // / - прямой потомок
        // // - непрямой потомок
        //SelenideElement $margarita = $x("//h3[contains(.,'Маргарита')]/../button");
        $margarita.click();

        sleep(1000);

    }
}
