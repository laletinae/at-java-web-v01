package demo.part05;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class CinemaTests {
    @BeforeEach
    void setUp() {
        Configuration.browser = "firefox";
    }

    @Test
    void test01SimpleCinema() {

        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        $("input[name=age]").setValue("35");
        //String dateValue = "16.12.2025"; // Дата в формате YYYY-MM-DD
        String dateValue = "2025-12-16"; // Дата в формате YYYY-MM-DD
        $("input[name='date']").setValue(dateValue);
        $("input[name=session][value='3']").click();
        $("input[name=film][value=crime]").click();
        $("input[type=submit").click();
        $("div").shouldHave(text("Стоимость билета: 350 рублей."));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "data.csv", numLinesToSkip = 1)
    void test02_param_cinema(String age, String dateValue, String start, String film, String priceMessage) {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        $("input[name=age]").setValue(age);
        $("input[name='date']").setValue(dateValue); // Дата в формате YYYY-MM-DD
        $("input[name=session][value='" + start + "']").click();
        $("input[name=film][value=" + film + "]").click();
        $("input[type=submit").click();
        $("div").shouldHave(text(priceMessage));
        sleep(1_000);
    }

    static String getDateByWeekDay(int weekDay) {
        // Приводим к стандарту Java (1-7 = понедельник-воскресенье)
        DayOfWeek targetDay = DayOfWeek.of(weekDay);
        // Получаем текущую дату
        LocalDate today = LocalDate.now();
        // Находим ближайшую дату с нужным днём недели, начиная со следующего дня
        LocalDate resultDate = today.plusDays(1).with(TemporalAdjusters.nextOrSame(targetDay));
        // Форматируем в строку
        return resultDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }


    static Stream<Arguments> provideCinemaData() {
        Stream.Builder<Arguments> argumentsBuilder = Stream.builder();

        argumentsBuilder.add(Arguments.of("35", getDateByWeekDay(2), "1", "back", "Стоимость билета: 350 рублей."));
        argumentsBuilder.add(Arguments.of("20", getDateByWeekDay(1), "2", "crime", "Стоимость билета: 350 рублей."));
        argumentsBuilder.add(Arguments.of("5", getDateByWeekDay(3), "3", "king", "Стоимость билета: 100 рублей."));

        return argumentsBuilder.build();
    }

    @ParameterizedTest
    @MethodSource("provideCinemaData")
    void test03_source_cinema(String age, String dateValue, String start, String film, String priceMessage) {
        open("http://92.51.36.108:7777/sl.qa/cinema/index.php");
        System.out.println(dateValue);
        $("input[name=age]").setValue(age);
        $("input[name='date']").setValue(dateValue); // Дата в формате YYYY-MM-DD
        $("input[name=session][value='" + start + "']").click();
        $("input[name=film][value=" + film + "]").click();
        $("input[type=submit").click();
        $("div").shouldHave(text(priceMessage));
        sleep(1_000);
    }
}
