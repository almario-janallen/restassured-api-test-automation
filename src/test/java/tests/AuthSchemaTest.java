package tests;

import utils.Constants;
import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class AuthSchemaTest extends BaseTest {

    // -------------------------------------------------------------------------
    // POST /login (success)  →  auth_login_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for POST /login (valid credentials)")
    public void validateLoginSuccessSchema() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.LOGIN)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/auth_login_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /login (failure)  →  auth_error_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate error response schema for POST /login (missing password)")
    public void validateLoginErrorSchema() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.LOGIN)
        .then()
            .statusCode(400)
            .body(matchesJsonSchemaInClasspath("schemas/auth_error_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /register (success)  →  auth_register_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for POST /register (valid payload)")
    public void validateRegisterSuccessSchema() {
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.REGISTER)
        .then()
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/auth_register_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /register (failure)  →  auth_error_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate error response schema for POST /register (missing password)")
    public void validateRegisterErrorSchema() {
        String requestBody = "{ \"email\": \"sydney@fife\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.REGISTER)
        .then()
            .statusCode(400)
            .body(matchesJsonSchemaInClasspath("schemas/auth_error_schema.json"));
    }
}