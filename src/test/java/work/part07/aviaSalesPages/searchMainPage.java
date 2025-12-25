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
        searchTo = $("#search-destination-input"),
        findButton = $x("//form[@data-test-id='avia-form']//button[@data-test-id='form-submit']"),
        startDate = $x("//form[@data-test-id='avia-form']//button[@data-test-id='start-date-field']"),
        //startDate = $x("//form[@data-test-id='avia-form']//button[@data-test-id='departure-calendar-icon']"),
        startDateInModal = $x("//div[@id='avs-modal-container']//button[@data-test-id='start-date-field']"),

        //выпадающий список направлений, Сочи
        destSochi = $x("//ul[@id='avia_form_destination-menu']//li[contains(.,'Сочи')]"),
        //ul id avia_form_destination-menu

    //li id avia_form_destination-item-4 contains text Сочи


        day12012026 = $x("//td[@data-day='2026-01-12']"),
        //listToSochi = $x("//ul[@id='avia_form_destination-menu']//"),
        firstFoundDestination = $x("//li[@id='avia_form_destination-item-0']"),
        modal = $("#avs-modal-container");

    @Step("Поиск рейсов (задаём только дату)")
    public void search(String from, String to) {
        this.fromField.clear();
        this.fromField.setValue(from);

        //Поле куда
        //this.toField.clear();
        this.toField.click();
        this.destSochi.click();

        //sleep(1500000);

        //Поле "Когда" будет в модальном окне
        this.startDate.shouldBe(visible);
        this.startDate.shouldBe(clickable);
        this.startDate.click();
        //this.startDateInModal.click();
        //this.startDate.click();


        //this.day12012026.click();


        this.findButton.click();

        //sleep(1500000);

        //exit(1);



        //Окно не открывается первый раз. Только выпадающий список
        //Открывается модальное окно поиска
        //modal.shouldBe(visible);
        //Открывшийся поиск направления Куда в модальном окне
        //this.searchTo.type(to);

        //Первый найденный в списке
        //this.firstFoundDestination.click();


    }

    @Step("Проверка что рейсы найдены")
    public void isFound() {
        //this.fromField.setValue(from);
        this.findButton.click();
    }
}
