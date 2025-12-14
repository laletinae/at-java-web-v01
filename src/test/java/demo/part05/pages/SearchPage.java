package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import com.codeborne.selenide.SelenideElement;

public class SearchPage {
    SelenideElement
            cityFrom = $("#departureCity"),
            cityTo = $("#arrivalCity"),
            departureDate = $("#departureDate"),
            findButton = $x("//button[.='Найти']"),
            message = $("#searchMessage");

    public void search(String departureDate) {
        this.departureDate.setValue(departureDate);
        this.findButton.click();
    }

    public void search(String departureDate, String from, String to) {
        this.departureDate.setValue(departureDate);
        this.cityFrom.selectOption(from);
        this.cityTo.selectOption(to);
        this.findButton.click();
    }

    public void isDepartureDateEmpty() {
        this.message.shouldHave(text("Пожалуйста, укажите дату вылета."));
    }
}