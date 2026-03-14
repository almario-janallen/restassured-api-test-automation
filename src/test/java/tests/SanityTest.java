package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.Constants;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class SanityTest extends BaseTest {

    @BeforeSuite(alwaysRun = true)
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(groups = {"sanity"})
    public void verifyApiIsReachable() {
        Response response = given(requestSpec)
                .queryParam("page", 1)
                .when()
                .get(Constants.USERS)
                .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("page", equalTo(1))
                .body("data", notNullValue())
                .extract()
                .response();

        System.out.println("✅ Sanity check passed. Status: " + response.getStatusCode());
    }
}
