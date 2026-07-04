package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Book;
import models.BookCollection;
import specs.ApiSpec;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BookApi {

    @Step("Получить список книг пользователя")
    public List<Book> getUserBooks(String userId, String token) {
        return given()
                .spec(ApiSpec.requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/Account/v1/User/" + userId)
                .then()
                .spec(ApiSpec.responseSpec200)
                .extract()
                .path("books");
    }

    @Step("Добавить книгу с ISBN {isbn}")
    public Response addBook(String userId, String isbn, String token) {
        BookCollection requestBody = new BookCollection(userId, isbn);

        return given()
                .spec(ApiSpec.requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(ApiSpec.responseSpec201)
                .extract()
                .response();
    }

    @Step("Удалить книгу с ISBN {isbn}")
    public void deleteBook(String userId, String isbn, String token) {
        DeleteBookRequest requestBody = new DeleteBookRequest(userId, isbn);

        given()
                .spec(ApiSpec.requestSpec)
                .header("Authorization", "Bearer " + token)
                .body(requestBody)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .spec(ApiSpec.responseSpec204);
    }

    @Step("Удалить все книги пользователя")
    public void deleteAllBooks(String userId, String token) {
        given()
                .spec(ApiSpec.requestSpec)
                .header("Authorization", "Bearer " + token)
                .queryParam("UserId", userId)
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .statusCode(204);
    }

    @Step("Проверить наличие книги в списке пользователя")
    public boolean isBookPresent(String userId, String isbn, String token) {
        List<Book> books = getUserBooks(userId, token);
        return books.stream().anyMatch(book -> book.getIsbn().equals(isbn));
    }

    private static class DeleteBookRequest {
        private final String isbn;
        private final String userId;

        public DeleteBookRequest(String userId, String isbn) {
            this.isbn = isbn;
            this.userId = userId;
        }

        public String getIsbn() {
            return isbn;
        }

        public String getUserId() {
            return userId;
        }
    }
}