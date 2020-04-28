package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredExample {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://official-joke-api.appspot.com";
    }

    @Test
    public void GeneralRandomJoke() {
        Response response = given().header("accept", "text/html")
                .log().all()
                .when().get("/jokes/general/random");

        response
                .then().assertThat().statusCode(200)
                .and().assertThat().body("[0].type", equalTo("general"))
                .and().assertThat().body(matchesJsonSchemaInClasspath("general-joke-schema.json"));
        /*
         *   [
         *       {
         *           "id": 125,
         *           "type": "general",
         *           "setup": "How do you fix a damaged jack-o-lantern?",
         *           "punchline": "You use a pumpkin patch."
         *       }
         *   ]
         */
    }

    @Test
    public void ProgrammingRandomJoke() {
        Response response = when()
                .get("/jokes/programming/random");

        response
                .then().assertThat().statusCode(200)
                .and().assertThat().body("[0].type", equalTo("programming"));
    }
}
