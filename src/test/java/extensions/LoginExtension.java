package extensions;

import annotations.WithLogin;
import api.AuthApi;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.Cookie;

public class LoginExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        var method = context.getRequiredTestMethod();
        var withLogin = method.getAnnotation(WithLogin.class);

        if (withLogin != null) {
            performLogin();
        }
    }

    @Step("Выполнить авторизацию через API и установить cookies")
    private void performLogin() {
        AuthApi authApi = new AuthApi();
        String token = authApi.login();
        String userId = authApi.getUserId();
        String username = authApi.getUsername();

        Selenide.open("https://demoqa.com");

        WebDriverRunner.getWebDriver().manage().deleteAllCookies();

        WebDriverRunner.getWebDriver().manage().addCookie(
                new Cookie("token", token)
        );
        WebDriverRunner.getWebDriver().manage().addCookie(
                new Cookie("userID", userId)
        );
        WebDriverRunner.getWebDriver().manage().addCookie(
                new Cookie("userName", username)
        );
        WebDriverRunner.getWebDriver().manage().addCookie(
                new Cookie("expires", "2026-07-11T18%3A57%3A36.648Z")
        );
    }
}