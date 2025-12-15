package work.part05;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

/*
 Домашнее задание на 21 декабря, обязательная часть
 AT-2 (обязательное):  Для формы расчёта стоимости билета в кино:
 http://92.51.36.108:7777/sl.qa/cinema/index.php
 напишите минимум 5 тест-кейсов и автоматизируйте их любым доступным вам способом.
 Требования к форме см.
 на этом слайде: https://docs.google.com/presentation/d/1QwFokl3ghAUMXWeiCRO5oILF4Hcznrq60-pyP13Xfxw/edit?slide=id.g34b94a5208e_0_0#slide=id.g34b94a5208e_0_0

 Поле возраст (число полных лет) - целое число от 0 до 99
    - 3 класса эквивалентности (<0; 0-12; 12-18; 18-99; > 99)
    - граничные значения: -1, 0, 1, 11, 12, 13, 17, 18, 19, 99, 100
 Дата сеанса - 7 дней подряд начиная с завтрашнего числа (на сегодня билет купить уже нельзя)
    - 3 класса эквивалентности (<= сегодня; от сегодня+1 день до сегодня+8 дней; сегодня+9дней);
    - граничные значения: вчера, сегодня, завтра, сегодня+8, сегодня+9
 Начало сеанса - возможные значения: 09:30, 11:00, 12:30, 14:00, 16:00, 17:30, 19:00, 20:30, 22:00, 23:30
    - 2 класса эквивалентности (< 14:00; >= 14:00)
    - граничные значения (??) 12:30, 14:00, 16:00
 Фильм - возможные значения: Назад в будущее - PG (12+), Криминальное чтиво - R (18+), Король-лев - G (0+),
 Прирождённые убийцы - NC-17 (18+), Последнее танго в Париже - NC-17 (18+).
 Фильмы с маркировкой "G (0+)" разрешены для любого возраста,
 "PG (12+)" - только с 12 лет, "R (18+)" и "NC-17 (18+)" - только с 18 лет.
 Если указан возраст, недостаточный для просмотра фильма,
 то при нажатии кнопки "Рассчитать" вместо стоимости билета выводится сообщение о том, что возраст не подходящий.
    - классы эквивалентности: фильмы 0+, фильмы 12+, фильмы 18+
    - граничные значения??
 Заполнение полей.
 Если какие-то из полей не заполнены, то при нажатии кнопки "Рассчитать" вместо стоимости билета выводится сообщение
 о том, какие поля не заполнены.
 Цена.
 Цена билета 500 рублей, но предусмотрены следующие скидки:
 Скидка для детей: детям, которым ещё не исполнилось 6 лет скидка 250 рублей.
 Скидка буднего дня: по будним дням скидка 100 рублей.
 Скидка на утренние сеансы: на сеансы до 14:00 скидка 50 рублей.
 Все скидки суммируются.

 */
public class CinemaPriceTests {

    //Дата сеанса
    SelenideElement $sessionDate = $x("//input[@name='date']");

    //Начало сеанса
    SelenideElement $sessionTime = $x("//input[@name='session']");

    //Название фильма
    SelenideElement $filmName = $x("//input[@name='film']");

    //Кнопка рассчитать
    SelenideElement $submitBtn = $x("//input[@type='submit']");

    @BeforeAll
    static void beforeAll(TestInfo test_info) {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        getWebDriver().manage().window().maximize();
    }
    @Test
    void countPrice() {
        $sessionDate.sendKeys("14.12.2025");

        $sessionTime.click();
        //$sessionTime.setValue("1");

        //$filmName.selectOption("Назад в будущее - PG (12+)");

        $submitBtn.click();

        sleep(1000);


    }

}
