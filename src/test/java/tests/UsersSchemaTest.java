package tests;

import utils.Constants;
import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class UsersSchemaTest extends BaseTest {

    // -------------------------------------------------------------------------
    // GET /users  →  user_list_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for GET /users (list)")
    public void validateUserListSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get(Constants.USERS)
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/user_list_schema.json"));
    }

    // -------------------------------------------------------------------------
    // GET /users/{id}  →  user_single_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for GET /users/{id} (single user)")
    public void validateUserSingleSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get(Constants.USERS + "/2")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/user_single_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /users  →  user_create_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for POST /users (create user)")
    public void validateUserCreateSchema() {
        String requestBody = "{ \"name\": \"John Doe\", \"job\": \"QA Engineer\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.USERS)
        .then()
            .statusCode(201)
            .body(matchesJsonSchemaInClasspath("schemas/user_create_schema.json"));
    }

    // -------------------------------------------------------------------------
    // PUT /users/{id}  →  user_update_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for PUT /users/{id} (full update)")
    public void validateUserPutSchema() {
        String requestBody = "{ \"name\": \"Jane Doe\", \"job\": \"Senior QA\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .put(Constants.USERS + "/2")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/user_update_schema.json"));
    }

    // -------------------------------------------------------------------------
    // PATCH /users/{id}  →  user_update_schema.json  (same shape as PUT)
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for PATCH /users/{id} (partial update)")
    public void validateUserPatchSchema() {
        String requestBody = "{ \"job\": \"Lead QA\" }";

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .patch(Constants.USERS + "/2")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/user_update_schema.json"));
    }
}