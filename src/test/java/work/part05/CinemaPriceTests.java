package work.part05;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import static com.codeborne.selenide.Condition.text;
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

    //Возраст
    SelenideElement age = $x("//input[@name='age']");

    //Дата сеанса
    SelenideElement sessionDate = $x("//input[@name='date']");



    //Фильмы
    /*
    SelenideElement back = $x("//input[@name='film']").selectRadio("back"); //Назад в будущее - PG (12+)
    SelenideElement crime = $x("//input[@name='film']").selectRadio("crime"); //Криминальное чтиво - R (18+)
    SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)
    SelenideElement killers = $x("//input[@name='film']").selectRadio("killers"); //Прирождённые убийцы - NC-17 (18+)
    SelenideElement tango = $x("//input[@name='film']").selectRadio("tango"); //Последнее танго в Париже - NC-17 (18+)
    */


    //Начало сеанса 09:30
    //SelenideElement $sessionTime0930 = $x("//input[@name='session']").selectRadio("1");


    //Кнопка рассчитать
    SelenideElement submitBtn = $x("//input[@type='submit']");

    //Сообщение о стоимости и об ошибках
    SelenideElement message = $x("//div[contains(text(),'Стоимость билета:')]");

    static String today;
    static String todayPlus1;
    static String todayPlus8;
    static String nearestMonday;
    static String nearestSaturday;

    @BeforeAll
    static void beforeAll(TestInfo test_info) {
        Configuration.pageLoadTimeout = 120_000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.browser = "firefox";
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        getWebDriver().manage().window().maximize();
        SelenideLogger.addListener("allure", new AllureSelenide());

        // Получаем текущую дату
        Date currentDate = new Date();
        //Можно взять конкретную дату для проверки
        //Date currentDate = new Date(2025 - 1900, 11, 21);

        // Создаем форматировщик
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Такое значение как задано в html - работает
        //SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy"); // так не работает несмотря на то что визуально в браузере поле с точками

        // Форматируем в строку
        today = sdf.format(currentDate);

        // Use Calendar to add one day
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate); // Set the calendar time to the current date

        c.add(Calendar.DAY_OF_MONTH, 1); // Add 1 day


        // Get the new date from the Calendar object
        //Date newDate = c.getTime();

        // Форматируем в строку
        todayPlus1 = sdf.format(c.getTime());
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        //Прибавляем ещё дни, исключая уже прибавленный и текущий
        c.add(Calendar.DAY_OF_MONTH, 6);

        // Форматируем в строку
        todayPlus8 = sdf.format(c.getTime());

        //вернулись обратно в тот день (сегодня +1 , т.е. тот день начиная с которого можно брать билеты)
        c.add(Calendar.DAY_OF_MONTH, -6);

        switch (String.valueOf(dayOfWeek)){
            case "1":
                System.out.println(1);
                c.add(Calendar.DAY_OF_MONTH, 1);
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, 5);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "2":
                System.out.println(2);
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, 5);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "3":
                System.out.println(3);
                c.add(Calendar.DAY_OF_MONTH, 6);
                //Date newDate = c.getTime();
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, -3);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "4":
                System.out.println(4);
                c.add(Calendar.DAY_OF_MONTH, 5);
                //Date newDate = c.getTime();
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, -2);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "5":
                System.out.println(5);
                c.add(Calendar.DAY_OF_MONTH, 4);
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, -2);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "6":
                System.out.println(6);
                c.add(Calendar.DAY_OF_MONTH, 3);
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, -2);
                nearestSaturday = sdf.format(c.getTime());
                break;
            case "7":
                System.out.println(7);
                c.add(Calendar.DAY_OF_MONTH, 2);
                nearestMonday = sdf.format(c.getTime());
                c.add(Calendar.DAY_OF_MONTH, -2);
                nearestSaturday = sdf.format(c.getTime());
                break;
        }

        System.out.println("Сегодня today: " + today);

        System.out.println("Ближайшиё ПН nearestMonday: " + nearestMonday);
        System.out.println("Ближайшая СБ nearestSaturday: " + nearestSaturday);


        System.out.println("Сегодня + 1 день todayPlus1: " + todayPlus1);
        System.out.println("Сегодня + 8 дней todayPlus8: " + todayPlus8);


    }

    /**
     * Тест-кейс 1
     * ======================================================================
     * Проверить что на фильмы 12+ нельзя взять билеты с возрастом < 12
     * Дата сеанса - завтра
     * Время сеанса 9:30
     */
    @ParameterizedTest (name = "Проверка возраста до 12+, #{index}, возраст: {0}")
    //@ValueSource(strings = {"09:30","11:00","12:30","14:00","16:00","17:30","19:00","20:30","22:00","23:30"})
    //@ValueSource(strings  = {"0","1","2","3","4","5","6","7","8","9","10","11"})
    @ValueSource(strings  = {"0","6","10","11"})
    //@ValueSource(strings  = {"0","12"})
    void test01TwelvePlus(String ageValues) {
        //Возраст
        age.sendKeys(ageValues);

        //Дата сеанса завтра
        sessionDate.sendKeys(todayPlus1);

        //Дата сеанса неделя после завтра (сегодня + 8 дней)
        sessionDate.sendKeys(todayPlus8);

        System.out.println("Завтра: " + todayPlus1);
        System.out.println("Неделя после завтра: " + todayPlus8);
        //sleep(2000);
        //exit(1);

        //Время сеанса 9:30
        SelenideElement sessionTime0930 = $x("//input[@name='session']").selectRadio("1");

        //Название фильма <input type="radio" name="film" value="killers">Прирождённые убийцы - NC-17 (18+)
        //Вариант по тексту - не работает
        //SelenideElement $filmName = $x("//input[@name='film' and contains(.,'Прирождённые убийцы - NC-17 (18+)')]");
        //Вариант по значению кнопки - работает (и для $ и для $x)
        //SelenideElement $filmName = $(By.name("film")).selectRadio("killers");
        //SelenideElement $filmName = $x("//input[@name='film']").selectRadio("killers");
        // => выносим в поля класса все кнопки??? Т.е. с кнопкой одинакового имени нельзя работать как с одним элементом (?)



        SelenideElement back = $x("//input[@name='film']").selectRadio("back"); //Назад в будущее - PG (12+)
        submitBtn.click();
        message.shouldHave(text("Этот фильм можно смотреть только с 12 лет"));
        sleep(1000);

        SelenideElement crime = $x("//input[@name='film']").selectRadio("crime"); //Криминальное чтиво - R (18+)
        submitBtn.click();
        message.shouldHave(text("Этот фильм можно смотреть только с 18 лет"));
        sleep(1000);

        SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)
        submitBtn.click();
        message.shouldNotHave(text("Этот фильм можно смотреть только с"));
        sleep(1000);

        SelenideElement killers = $x("//input[@name='film']").selectRadio("killers"); //Прирождённые убийцы - NC-17 (18+)
        submitBtn.click();
        message.shouldHave(text("Этот фильм можно смотреть только с 18 лет"));
        sleep(1000);
        //back.clear();

        SelenideElement tango = $x("//input[@name='film']").selectRadio("tango"); //Последнее танго в Париже - NC-17 (18+)
        submitBtn.click();
        message.shouldHave(text("Этот фильм можно смотреть только с 18 лет"));
        sleep(1000);

        age.clear();
        //sleep(1000);
    }

    /**
     * Тест-кейс 2
     * ======================================================================
     * Проверить что форма не отправляется если возраст более 99 лет
     */
    @Test
    void test02Age100() {
        //Возраст
        age.sendKeys("100");

        //Дата сеанса завтра
        sessionDate.sendKeys(todayPlus1);

        //Время сеанса 9:30
        SelenideElement sessionTime0930 = $x("//input[@name='session']").selectRadio("1");

        //Фильм король-лев
        SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)

        submitBtn.click();

        //Проверяем что текст 100 нельзя установить, т.к. задано макс.значение 99
        //Несмотря на то что у элемента чётко задано value=100, текст возращается пустой при проверке should()
        age.shouldNotHave(text("100"));

        //age.clear();
        //sleep(1000);
    }

    /**
     * Тест-кейс 3
     * ======================================================================
     * Проверить что все скидки суммируются
     *  Цена билета 500 рублей, но предусмотрены следующие скидки:
     *  Скидка для детей: детям, которым ещё не исполнилось 6 лет скидка 250 рублей.
     *  Скидка буднего дня: по будним дням скидка 100 рублей.
     *  Скидка на утренние сеансы: на сеансы до 14:00 скидка 50 рублей.
     *  Все скидки суммируются.
     */
    @Test
    void test03SkidkiSumm() {
        //Возраст
        age.sendKeys("5");

        //Дата сеанса 22.12.2025 (ПН)
        sessionDate.sendKeys("2025-12-22");

        //Время сеанса 9:30
        SelenideElement sessionTime0930 = $x("//input[@name='session']").selectRadio("1");

        //Фильм король-лев
        SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)

        submitBtn.click();


        var price = 500 - 250 - 100 - 50;
        message.shouldHave(text("Стоимость билета: " + price + " рублей."));

        //age.clear();
        //sleep(10000);
    }

    /**
     * Тест-кейс 4
     * ======================================================================
     * Проверить что на утренние сеансы есть скидка (до 14:00 скидка 50 рублей)
     */
    @Test
    void test04SessionTimeDiscount() {

        var price = 500 - 50;

        //Возраст
        age.sendKeys("18");

        //Дата сеанса ближайшая СБ (нет скидки)
        sessionDate.sendKeys(nearestSaturday);

        //Фильм король-лев
        SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)

        //Время сеанса 9:30
        SelenideElement sessionTime0930 = $x("//input[@name='session']").selectRadio("1");
        submitBtn.click();
        message.shouldHave(text("Стоимость билета: " + price + " рублей."));
        //sleep(5000);

        //Время сеанса 11:00
        SelenideElement sessionTime1100 = $x("//input[@name='session']").selectRadio("2");
        submitBtn.click();
        message.shouldHave(text("Стоимость билета: " + price + " рублей."));
        //sleep(5000);

        //Время сеанса 12:30
        SelenideElement sessionTime1230 = $x("//input[@name='session']").selectRadio("3");
        submitBtn.click();
        message.shouldHave(text("Стоимость билета: " + price + " рублей."));

        //age.clear();
        //sleep(5000);
    }


    /**
     * Тест-кейс 5
     * ======================================================================
     * Проверить параметризованным тестом несколько значений цены, сеанса и др.
     */
    @ParameterizedTest (name = "Проверка цен, #{index}, возраст: {0}, дата: {1}, сеанс: {2}, цена: {3}")
    @CsvFileSource(resources = "cinema_prices_check.csv", numLinesToSkip = 1)
    //@ValueSource(strings  = {"0","12"})
    void test05PriceCount(String ageValue, String dateValue, String sessionValue, String priceValue) {

        //Возраст
        age.sendKeys(ageValue);

        //Дата
        sessionDate.sendKeys(dateValue);

        //Фильм король-лев
        SelenideElement king = $x("//input[@name='film']").selectRadio("king"); //Король-лев - G (0+)

        //Время сеанса 9:30
        SelenideElement sessionTime0930 = $x("//input[@name='session']").selectRadio(sessionValue);
        submitBtn.click();

        message.shouldHave(text("Стоимость билета: " + priceValue + " рублей."));

        age.clear();
    }

}
