package tests;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.ResourceAPI;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;

@Epic("Resource API")
public class ResourceSchemaTest extends BaseTest {
    private ResourceAPI resourceAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        resourceAPI = new ResourceAPI();
    }

    // -------------------------------------------------------------------------
    // GET /unknown  →  resource_list_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Get Resource")
    @Story("Get All Resources Schema")
    @Description("Validate response schema for GET /unknown (resource list)")
    @Severity(SeverityLevel.NORMAL)
    public void validateResourceListSchema() {
        Response response = resourceAPI.getResourceList();
        
        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/resource_list_schema.json"));
    }

    // -------------------------------------------------------------------------
    // GET /unknown/{id}  →  resource_single_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Get Resource")
    @Story("Get Specific Resource Schema")
    @Description("Validate response schema for GET /unknown/{id} (single resource)")
    @Severity(SeverityLevel.NORMAL)
    public void validateResourceSingleSchema() {
        Response response = resourceAPI.getResource(1);
        
        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/resource_single_schema.json"));
    }
}