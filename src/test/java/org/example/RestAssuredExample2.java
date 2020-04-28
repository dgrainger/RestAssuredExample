package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssuredExample2 {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void PostmanEchoGet() {
        String endpoint = "/get";
        String param1key = "foo1";
        String param1val = "bar1";
        String param2key = "foo2";
        String param2val = "bar2";

        // https://postman-echo.com/get?foo1=bar1&foo2=bar2
        String fullURL = String.format("%s%s?%s=%s&%s=%s",
                RestAssured.baseURI, endpoint, param1key, param1val, param2key, param2val);

        Response response = given()
                .param(param1key, param1val)
                .param(param2key, param2val)
                .log().params()
                .when()
                .get(endpoint);
        response
                .then().assertThat().statusCode(200)
                .and().assertThat().body("url", equalTo(fullURL));
    }

    @Test
    public void PostmanEchoPut() {
        String endpoint = "/put";
        String echoString = "This string gets echoed back";

        Response response = given()
                .body(echoString)
                .log().body()
                .when()
                .put(endpoint);
        response
                .then().assertThat().statusCode(200)
                .and().assertThat().body("data", equalTo(echoString));
    }

    @Test
    public void PostmanEchoBasicAuth() {
        String endpoint = "/basic-auth";

        Response response = given()
                .auth().basic("postman", "password")
                .when()
                .get(endpoint);
        response
                .then().log().ifStatusCodeIsEqualTo(400)
                .and().assertThat().statusCode(200)
                .and().assertThat().body("authenticated", equalTo(true));
        /* watch out for data type in a matcher, boolean true != String "true"
         * if you use "true" rather the true, the error message when this matcher fails is:
         *
         *  expected: true
         *    actual: true
         *
         * The same holds true for "5" != 5 and more subtly 12.0f != 12.0 because float != double
         *
         */
    }
}
