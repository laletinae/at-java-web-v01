package pages;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import com.codeborne.selenide.SelenideElement;

public class FlightsListPage {
    SelenideElement
            flightsTable = $("#flightsTable"),
            registerButton = $x("//button[.='Зарегистрироваться']");

    public void isNoFlights() {
        flightsTable.shouldHave(text("Рейсов по вашему запросу не найдено."));
    }

    public void registerToFirstFlight() {
        this.registerButton.click();
    }

}