package tests;

import annotations.WithLogin;
import api.AuthApi;
import api.BookApi;
import base.BaseTest;
import helpers.ConfigHelper;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.ProfilePage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("UI Тесты")
@Feature("Управление книгами")
public class BookDeleteTest extends BaseTest {

    private ProfilePage profilePage;
    private BookApi bookApi;
    private AuthApi authApi;
    private String userId;
    private String token;

    private final String testIsbn = ConfigHelper.getTestBookIsbn();
    private final String testBookTitle = ConfigHelper.getTestBookTitle();

    @BeforeEach
    void setUp() {
        profilePage = new ProfilePage();
        bookApi = new BookApi();
        authApi = new AuthApi();

        token = authApi.login();
        userId = authApi.getUserId();
    }

    @Test
    @WithLogin
    @DisplayName("Удаление книги из профиля")
    @Description("Тест проверяет удаление книги из профиля через комбинацию API и UI")
    @Story("Удаление книги из профиля пользователя")
    void shouldDeleteBookFromProfile() {
        bookApi.deleteAllBooks(userId, token);
        bookApi.addBook(userId, testIsbn, token);

        profilePage.openProfile();

        assertThat(profilePage.isBookPresent(testBookTitle))
                .as("Книга '%s' должна отображаться в профиле", testBookTitle)
                .isTrue();

        profilePage.deleteBook(testBookTitle);

        assertThat(profilePage.isBookAbsent(testBookTitle))
                .as("Книга '%s' должна исчезнуть после удаления", testBookTitle)
                .isTrue();

        var books = bookApi.getUserBooks(userId, token);
        assertThat(books)
                .as("Книга должна отсутствовать в API ответе")
                .noneMatch(book -> book.getIsbn().equals(testIsbn));
    }
}