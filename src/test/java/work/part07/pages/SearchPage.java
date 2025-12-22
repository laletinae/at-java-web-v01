package work.part07.pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class SearchPage {
    SelenideElement
            cityFrom = $("#departureCity"),
            cityTo = $("#arrivalCity"),
            departureDate = $("#departureDate"),
            findButton = $x("//button[.='Найти']"),
            message = $("#searchMessage"),
            flightsTable = $("#flightsTable"),
            msgNoFlights = $x("//*[@id='flightsContainer']//td[contains(.,'Рейсов по вашему запросу не найдено.')]"),
            buttonNewSearch = $x("//button[contains(.,'Новый поиск')]"),
            buttonReg = $x("//button[contains(.,'Зарегистрироваться')]");


    @Step("Поиск рейсов (задаём только дату)")
    public void search(String departureDate) {
        this.departureDate.setValue(departureDate);
        this.findButton.click();
    }

    @Step("Поиск рейсов")
    public void search(String departureDate, String from, String to) {
        this.departureDate.setValue(departureDate);
        this.cityFrom.selectOption(from);
        this.cityTo.selectOption(to);
        this.findButton.click();
    }

    @Step("Проверка, что дата не указана")
    public void isDepartureDateEmpty() {
        this.message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }

    @Step("Проверка, что дата в прошлом")
    public void isDepartureDateInPast() {
        this.message.shouldHave(text("Невозможно осуществить поиск: выбранная дата уже прошла."));
    }

    @Step("Проверка, что рейсов не найдено")
    public void isNoFlightsFound() {
        this.msgNoFlights.should(exist);
    }

    @Step("Проверка, что рейсы найдены")
    public void isFlightsFound() {
        this.flightsTable.should(exist);
    }

    @Step("Возврат на страницу поиска")
    public void newSearch() {
        this.buttonNewSearch.click();
    }

    @Step("Переход на страницу регистрации")
    public void openReg() {
        this.buttonReg.click();
    }
}