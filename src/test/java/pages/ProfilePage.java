package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Condition.*;

public class ProfilePage {

    private final SelenideElement booksTable = $("table");
    private final SelenideElement modalOkButton = $("#closeSmallModal-ok");
    private final SelenideElement modalContent = $(".modal-content");
    private final SelenideElement noBooksMessage = $(".rt-noData");
    private final SelenideElement userNameElement = $("#userName-value");
    private final ElementsCollection bookRows = $$("table tbody tr");

    private static final Duration TIMEOUT = Duration.ofSeconds(10);
    private static final Duration SHORT_TIMEOUT = Duration.ofSeconds(5);

    @Step("Открыть страницу профиля")
    public ProfilePage openProfile() {
        Selenide.open("/profile");
        waitForPageLoaded();
        return this;
    }

    @Step("Ожидать загрузки страницы")
    public void waitForPageLoaded() {
        userNameElement.shouldBe(visible, TIMEOUT);
    }

    @Step("Ожидать загрузки таблицы с книгами")
    public void waitForBooksLoaded() {
        booksTable.shouldBe(visible, TIMEOUT);
    }

    @Step("Проверить наличие книги {bookTitle}")
    public boolean isBookPresent(String bookTitle) {
        try {
            waitForBooksLoaded();
            $(byText(bookTitle)).shouldBe(visible, SHORT_TIMEOUT);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Получить строку с книгой {bookTitle}")
    public SelenideElement getBookRowByTitle(String bookTitle) {
        waitForBooksLoaded();
        return $(byText(bookTitle)).closest("tr");
    }

    @Step("Нажать кнопку удаления для книги {bookTitle}")
    public void clickDeleteButtonByTitle(String bookTitle) {
        SelenideElement bookRow = getBookRowByTitle(bookTitle);
        SelenideElement deleteButton = bookRow.$("span[id^='delete-record-']");
        deleteButton.shouldBe(visible, SHORT_TIMEOUT);
        deleteButton.click();
    }

    @Step("Подтвердить удаление в модальном окне")
    public void confirmDelete() {
        modalContent.shouldBe(visible, TIMEOUT);
        modalOkButton.click();
        modalContent.shouldBe(disappear, TIMEOUT);
    }

    @Step("Удалить книгу {bookTitle} через UI")
    public ProfilePage deleteBook(String bookTitle) {
        clickDeleteButtonByTitle(bookTitle);
        confirmDelete();
        booksTable.shouldBe(visible, TIMEOUT);
        return this;
    }

    @Step("Проверить что книга {bookTitle} отсутствует")
    public boolean isBookAbsent(String bookTitle) {
        try {
            waitForBooksLoaded();
            boolean bookExists = $(byText(bookTitle)).exists();
            boolean hasNoBooks = noBooksMessage.exists() && noBooksMessage.isDisplayed();
            return !bookExists || hasNoBooks;
        } catch (Exception e) {
            return true;
        }
    }
}