package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import utils.Constants;
import utils.DataProviders;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class UsersDataDrivenTest extends BaseTest {

    // -------------------------------------------------------------------------
    // POST /users  —  driven by create_user.csv
    // Columns: name, job
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},
          dataProvider = "createUserData",
          dataProviderClass = DataProviders.class,
          description = "Create a user with multiple name/job combinations from CSV")
    public void testCreateUserDataDriven(String name, String job) {
        String requestBody = String.format("{ \"name\": \"%s\", \"job\": \"%s\" }", name, job);

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.USERS)
        .then()
            .statusCode(201)
            .body("name",      equalTo(name))
            .body("job",       equalTo(job))
            .body("id",        notNullValue())
            .body("createdAt", notNullValue());
    }
}