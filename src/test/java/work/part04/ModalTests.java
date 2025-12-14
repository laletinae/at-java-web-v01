package work.part04;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ModalTests {
    @Test
    void test01SimpleModal() {
        Configuration.pageLoadStrategy = "eager";
        open("https://practice-automation.com/modals/");

        //Проверка существования модального окна (существует в дереве страницы даже если не актовно)
        $x("//div[@id='popmake-1318']").shouldBe(exist);

        //Кликаем по кнопке чтобы вызвать модальное окно
        $("#simpleModal").click();
        sleep(2_000);


        //элемент на странице не будет найден, т.к. перекрыт модальным окном
        //$x("//a[text()='Home']").click();

        //Закрыть модальное окно
        $x("//div[@id='popmake-1318']").shouldBe(visible);
        $x("//div[@id='popmake-1318']//button").shouldBe(clickable);
        $x("//div[@id='popmake-1318']//button").click();
        $x("//div[@id='popmake-1318']")
                .shouldBe(visible)
                .shouldBe(clickable);
        sleep(2_000);
    }
}