package work.part07.aviaSalesPages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class searchResults {
    SelenideElement
        newWindAdvert = $x("//div[@id='__next']"),
        directTicketsContainer = $x("//div[@data-test-id='direct-tickets-schedule-container']");

    ElementsCollection allFlightsPreviews = $$x("//div[@data-test-id='ticket-preview']");
    // ElementsCollection allFlightsPreviews = $$x("//div[@id='__next']");

    /*search-results-items-list
    direct-tickets-schedule-container
    data-test-id
    ticket-preview
    */

    @Step("Проверка что в найденных есть прямые рейсы")
    public void checkDirectTicketsExist() {
        //Если реклама в новом окне открылась - переключаемся
        if (this.newWindAdvert.exists()){
            switchTo().window(1);
        }
        this.directTicketsContainer.should(exist);
    }

    @Step("Вывод всех найденных рейсов из Превью")
    public void allFlightsPreviewsPrint() {
        for (SelenideElement flightPreview : allFlightsPreviews) {
            String priceText = flightPreview.$x(".//div[@data-test-id='price']").text().trim();
            System.out.println(priceText);
        }
    }


}
