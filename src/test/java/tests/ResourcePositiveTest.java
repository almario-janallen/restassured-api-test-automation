package tests;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

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
public class ResourcePositiveTest extends BaseTest {
    private ResourceAPI resourceAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        resourceAPI = new ResourceAPI();
    }

    @Test(groups = {"regression"})
    @Feature("Get Resource")
    @Story("Get Specific Resource")
    @Description("Verify getting specific resource returns 200 with expected fields (id, name ,year, color, pantone_value)")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSpecificResource() {
        Response response = resourceAPI.getResource(1);
        
        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data.id",notNullValue())
                .body("data.name",not(emptyString()))
                .body("data.year",notNullValue())
                .body("data.color",not(emptyString()))
                .body("data.pantone_value",not(emptyString()));
    }

    @Test(groups = {"smoke","regression"})
    @Feature("Get Resource")
    @Story("Get Resources")
    @Description("Verify getting all resources returns 200 and a non-empty data array")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllResources() {
        Response response = resourceAPI.getResourceList();
        
        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data",not(empty()));
    }

    @Test(groups = {"regression"})
    @Feature("Get Resource")
    @Story("Get Resources Per Page")
    @Description("Verify getting resource with page query returns 200 and a non-empty data array")
    @Severity(SeverityLevel.NORMAL)
    public void testGetAllResourcesPerPage() {
        Response response = resourceAPI.getResourceList(1);
        
        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data",not(empty()))
                .body("page",equalTo(1));
    }
}
