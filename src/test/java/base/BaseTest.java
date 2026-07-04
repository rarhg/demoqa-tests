package base;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import extensions.LoginExtension;
import helpers.ConfigHelper;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(LoginExtension.class)
public class BaseTest {

    @BeforeAll
    static void setUpAll() {
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browser.size", "1920x1080");
        Configuration.timeout = Long.parseLong(System.getProperty("timeout", "15000"));
        Configuration.pageLoadTimeout = 30000;
        Configuration.baseUrl = ConfigHelper.getBaseUrl();
        Configuration.headless = false;

        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()
                .screenshots(true)
                .savePageSource(true)
                .includeSelenideSteps(true));
    }
}