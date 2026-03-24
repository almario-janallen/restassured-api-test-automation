package tests;

import base.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.CreateUserRequest;
import models.UpdateUserRequest;
import utils.ConfigReader;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.UserAPI;

@Epic("User API")
public class UsersNegativeTest extends BaseTest {
    private UserAPI userAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        userAPI = new UserAPI();
    }
    
    @Test(groups = {"regression"})
    @Feature("Get User")
    @Story("Get Nonexistent User")
    @Description("Verify retrieving a user with non-existent ID returns 404 and an empty body")
    @Severity(SeverityLevel.NORMAL)
    public void testNonExistentUser() {
        Response response = userAPI.getUser(999);

        response.then()
                .spec(responseSpec)
                .statusCode(404)
                .time(lessThan(ConfigReader.getLong("response.timeout.ms")))
                .body(equalTo("{}"));
    }

    @Test(groups = {"regression"})
    @Feature("Create User")
    @Story("Create Empty Body User")
    @Description("Verify creating a user with empty body returns 201")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateEmptyBodyUser() {
        CreateUserRequest createUser = new CreateUserRequest();
        Response response = userAPI.createUser(createUser);

        response.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("id",notNullValue())
                .body("createdAt",notNullValue());
    }

    @Test(groups = {"regression"})
    @Feature("Update User")
    @Story("Update Nonexistent User")
    @Description("Verify updating non-existing user returns 200")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateNonExistentUser() {
        UpdateUserRequest updateUser = new UpdateUserRequest("morpheus","zion resident");

        Response response = userAPI.updateUser(999, updateUser );

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("updatedAt",notNullValue());
    }

    @Test(groups = {"regression"})
    @Feature("Delete User")
    @Story("Delete Nonexistent User")
    @Description("Verify deleting non-existent user returns 204")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteNonExistentUser() {
        Response response = userAPI.deleteUser(999);

        response.then()
                .statusCode(204)
                .time(lessThan(ConfigReader.getLong("response.timeout.ms")));
    }
}
