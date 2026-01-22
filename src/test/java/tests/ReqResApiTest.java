package tests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ReqResApiTest {

    @Test
    public void getUsers_shouldReturn200_andDataArray() {
        RestAssured.baseURI = "https://reqres.in";

        given()
            .queryParam("page", 2)
        .when()
            .get("/api/users")
        .then()
            .statusCode(200)
            .body("data", is(notNullValue()))
            .body("data.size()", greaterThan(0));
    }

    @Test
    public void createUser_shouldReturn201_andId() {
        RestAssured.baseURI = "https://reqres.in";

        String payload = "{ \"name\": \"Prashanth\", \"job\": \"QA Automation Engineer\" }";

        given()
            .header("Content-Type", "application/json")
            .body(payload)
        .when()
            .post("/api/users")
        .then()
            .statusCode(201)
            .body("id", notNullValue())
            .body("createdAt", notNullValue());
    }
}
