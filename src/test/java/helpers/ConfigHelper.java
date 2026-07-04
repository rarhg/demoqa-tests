package helpers;

import org.aeonbits.owner.ConfigFactory;

public class ConfigHelper {

    private static final AppConfig CONFIG = ConfigFactory.create(AppConfig.class);

    public static String getBaseUrl() {
        return CONFIG.baseUrl();
    }

    public static String getUserName() {
        return CONFIG.userName();
    }

    public static String getUserPassword() {
        return CONFIG.userPassword();
    }

    public static String getUserId() {
        return CONFIG.userId();
    }

    public static String getUserToken() {
        return CONFIG.userToken();
    }

    public static String getTestBookIsbn() {
        return CONFIG.testBookIsbn();
    }

    public static String getTestBookTitle() {
        return CONFIG.testBookTitle();
    }
}