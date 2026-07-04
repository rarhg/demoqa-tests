package specs;

import helpers.ConfigHelper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class ApiSpec {

    public static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(ConfigHelper.getBaseUrl())
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectResponseTime(lessThan(10000L))
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectResponseTime(lessThan(10000L))
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification responseSpec204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .expectResponseTime(lessThan(10000L))
            .log(LogDetail.ALL)
            .build();
}