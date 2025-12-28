package work.part07.pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class FlightsListPage {
    SelenideElement
            flightsTable = $("#flightsTable"),
            registerButton = $x("//button[.='Зарегистрироваться']"),
            sortField = $("#sortField"),
            ascOrDescField = $("#sortDirection");

    ElementsCollection allFlights = $$x("//table/tbody/tr");

    @Step("Выбираем первый рейс в списке")
    public void registerToFirstFlight() {
        this.registerButton.click();
    }

    @Step("Проверяем, что рейсов не найдено")
    public void isNoFlights() {
        flightsTable.shouldHave(text("Рейсов по вашему запросу не найдено."));
    }


    /**
     * Сортировка по цене
     * asc - По возрастанию
     * desc - По убыванию
     * @param ascOrDesc - параметр сортировки asc или desc
     */
    public void sortByPrice(String ascOrDesc) {
        this.sortField.selectOptionByValue("price");
        this.ascOrDescField.selectOptionByValue(ascOrDesc);
    }

    public void sortByTime(String ascOrDesc) {
        this.sortField.selectOptionByValue("time");
        this.ascOrDescField.selectOptionByValue(ascOrDesc);
    }


    @Step("Проверка: отсортировано по времени по возрастанию")
    public void isTimeAscSorted() {
        LocalTime prev = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (SelenideElement currentFlight : allFlights) {
            String timeText = currentFlight.$x("./td[5]").text().trim();
            //System.out.println(timeText);
            LocalTime current = LocalTime.parse(timeText, formatter);

            if (prev != null) {
                Assertions.assertTrue(prev.isBefore(current));
            }
            prev = current;
        }
    }

    @Step("Проверка: отсортировано по времени по убыванию")
    public void isTimeDescSorted() {
        LocalTime prev = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        for (SelenideElement currentFlight : allFlights) {
            String timeText = currentFlight.$x("./td[5]").text().trim();
            //System.out.println(timeText);
            LocalTime current = LocalTime.parse(timeText, formatter);

            if (prev != null) {
                Assertions.assertTrue(prev.isAfter(current));
            }
            prev = current;
        }
    }

    @Step("Проверка: отсортировано по цене по возрастанию")
    public void isPriceAscSorted() {
        int prev = 0;

        for (SelenideElement currentFlight : allFlights) {
            String priceText = currentFlight.$x("./td[6]").text().trim();
            // Убираем всё кроме цифр и получаем целое число
            int current = Integer.parseInt(priceText.replaceAll("\\D", ""));

            if (prev != 0) {
                Assertions.assertTrue(prev <= current);
            }
            prev = current;
        }
    }

    @Step("Проверка: отсортировано по цене по убыванию")
    public void isPriceDescSorted() {
        int prev = 0;

        for (SelenideElement currentFlight : allFlights) {
            String priceText = currentFlight.$x("./td[6]").text().trim();
            // Убираем всё кроме цифр и получаем целое число
            int current = Integer.parseInt(priceText.replaceAll("\\D", ""));

            if (prev != 0) {
                Assertions.assertTrue(prev >= current);
            }
            prev = current;
        }
    }
}