package helpers;

import org.aeonbits.owner.Config;

@Config.Sources({
        "classpath:application.properties",
        "system:properties"
})
public interface AppConfig extends Config {

    @Key("base.url")
    String baseUrl();

    @Key("test.user.name")
    String userName();

    @Key("test.user.password")
    String userPassword();

    @Key("test.user.id")
    String userId();

    @Key("test.user.token")
    String userToken();

    @Key("test.book.isbn")
    String testBookIsbn();

    @Key("test.book.title")
    String testBookTitle();
}