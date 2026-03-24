package tests;

import static org.hamcrest.Matchers.equalTo;

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
public class ResourceNegativeTest extends BaseTest {
    private ResourceAPI resourceAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        resourceAPI = new ResourceAPI();
    }

    @Test(groups = {"regression"})
    @Feature("Get Resource")
    @Story("Get Nonexistent Resource")
    @Description("Verify retrieving non-existent resource returns 404 and an empty body")
    @Severity(SeverityLevel.NORMAL)
    public void testGetNonExistentResource() {
        Response response = resourceAPI.getResource(999);

        response.then()
                .spec(responseSpec)
                .statusCode(404)
                .body(equalTo("{}"));
    }
}
