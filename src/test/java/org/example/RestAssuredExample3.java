package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class RestAssuredExample3 {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://telus.com";
    }

    @Test
    public void Cookies() {
        String F5Cookie = "F5_GEO_PROV";
        String locationCookie = "prov";
        String realLocation = "ON";
        String setLocation = "AB";
        String endpoint = "en";

        Response response = given()
                .cookie(locationCookie, setLocation)
                .when()
                .get(endpoint);

        response
                .then().assertThat().cookie(F5Cookie, realLocation);
    }

    @Test
    public void DifferentEndpoint() {
        baseURI = "https://api.digital.telus.com";
        String endpoint = "/authentication/status";

        Response response = given()
                .param("timestamp", String.format("%d", System.currentTimeMillis()))
                .when()
                .get(endpoint);

        response.then().assertThat().statusCode(200)
                .and().assertThat().body("isValid", equalTo(false));
    }
}
