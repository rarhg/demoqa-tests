package api;

import helpers.ConfigHelper;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.LoginRequest;
import models.LoginResponse;
import specs.ApiSpec;

import static io.restassured.RestAssured.given;

public class AuthApi {

    private String token;
    private String userId;
    private String username;

    @Step("Выполнить вход с логином {userName}")
    public LoginResponse login(String userName, String password) {
        LoginRequest loginRequest = new LoginRequest(userName, password);

        Response response = given()
                .spec(ApiSpec.requestSpec)
                .body(loginRequest)
                .when()
                .post("/Account/v1/Login")
                .then()
                .spec(ApiSpec.responseSpec200)
                .extract()
                .response();

        LoginResponse loginResponse = response.as(LoginResponse.class);
        this.token = loginResponse.getToken();
        this.userId = loginResponse.getUserId();
        this.username = loginResponse.getUsername();

        return loginResponse;
    }

    @Step("Выполнить вход с данными из конфигурации")
    public String login() {
        LoginRequest loginRequest = new LoginRequest(
                ConfigHelper.getUserName(),
                ConfigHelper.getUserPassword()
        );
        login(loginRequest.getUserName(), loginRequest.getPassword());
        return token;
    }

    public String getToken() {
        return token;
    }

    public String getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}