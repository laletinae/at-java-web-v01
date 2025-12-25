package work.part07.aviaSalesPages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.clickable;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.System.exit;

public class searchMainPage {
    SelenideElement
        fromField = $("#avia_form_origin-input"),
        toField = $("#avia_form_destination-input"),
        findButton = $x("//form[@data-test-id='avia-form']//button[@data-test-id='form-submit']"),
        startDateField = $x("//form[@data-test-id='avia-form']//button[@data-test-id='start-date-field']"),
        //startDate = $x("//form[@data-test-id='avia-form']//button[@data-test-id='departure-calendar-icon']"),
        //startDateInModal = $x("//div[@id='avs-modal-container']//button[@data-test-id='start-date-field']"),

        //выпадающий список направлений
        destinationMenu = $x("//ul[@id='avia_form_destination-menu']"),

        //выпадающая таблица с выбором дат
        dropDownDatesTable = $x("//div[@data-test-id='dropdown']");

        //firstFoundDestination = $x("//li[@id='avia_form_destination-item-0']"),
        //modal = $("#avs-modal-container");

    @Step("Поиск рейсов по заданному направлению на дату")
    public void search(String from, String to, String date) {
        this.fromField.clear();
        this.fromField.setValue(from);

        //Поле куда (развернуть список для выбора)
        this.toField.click();

        //Выбираем направление (поиск по названию) из выпадающего списка
        SelenideElement destination = destinationMenu.$x("./li[contains(.,'" + to + "')]");
        destination.click();

        //Поле "Когда" нужно кликнуть чтобы появилась выпадающая таблица дат
        this.startDateField.shouldBe(visible);
        this.startDateField.shouldBe(clickable);
        this.startDateField.click();

        //Выбор конкретной даты
        SelenideElement startDate = dropDownDatesTable.$x(".//td[@data-day='" + date + "']");
        startDate.click();


        this.findButton.click();

        sleep(10000);

        //exit(1);



        //Окно не открывается первый раз. Только выпадающий список
        //Открывается модальное окно поиска
        //modal.shouldBe(visible);
        //Открывшийся поиск направления Куда в модальном окне
        //this.searchTo.type(to);

        //Первый найденный в списке
        //this.firstFoundDestination.click();


    }

}
