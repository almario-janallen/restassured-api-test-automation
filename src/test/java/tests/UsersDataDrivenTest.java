package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.CreateUserRequest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.UserAPI;
import utils.DataProviders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@Epic("User API")
public class UsersDataDrivenTest extends BaseTest {
    private UserAPI userAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        userAPI = new UserAPI();
    }

    // -------------------------------------------------------------------------
    // POST /users  —  driven by create_user.csv
    // Columns: name, job
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},dataProvider = "createUserData",dataProviderClass = DataProviders.class)
    @Feature("Create User")
    @Story("Create User - Data Driven")
    @Description("Create a user with multiple name/job combinations from CSV")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUserDataDriven(String name, String job) {
        CreateUserRequest request = new CreateUserRequest(name,job);
        Response response = userAPI.createUser(request);

        response.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name",      equalTo(name))
                .body("job",       equalTo(job))
                .body("id",        notNullValue())
                .body("createdAt", notNullValue());
    }
}