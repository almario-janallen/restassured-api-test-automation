package tests;

import utils.Constants;
import base.BaseTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;

public class ResourceSchemaTest extends BaseTest {

    // -------------------------------------------------------------------------
    // GET /unknown  →  resource_list_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for GET /unknown (resource list)")
    public void validateResourceListSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get(Constants.RESOURCES)
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/resource_list_schema.json"));
    }

    // -------------------------------------------------------------------------
    // GET /unknown/{id}  →  resource_single_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"},
          description = "Validate response schema for GET /unknown/{id} (single resource)")
    public void validateResourceSingleSchema() {
        given()
            .spec(requestSpec)
        .when()
            .get(Constants.RESOURCES + "/2")
        .then()
            .spec(responseSpec)
            .body(matchesJsonSchemaInClasspath("schemas/resource_single_schema.json"));
    }
}