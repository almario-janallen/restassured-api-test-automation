package tests;

import base.BaseTest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import models.CreateUserRequest;
import models.UpdateUserRequest;
import utils.ConfigReader;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.not;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.UserAPI;

public class UsersPositiveTest extends BaseTest {
    private UserAPI userAPI;

    @BeforeClass
    public void setUp() {
        userAPI = new UserAPI();
    }
    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Get Users")
    @Description("Verify listing all users returns 200 and a non-empty data array")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllUsers() {
        Response response = userAPI.getUserList();

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data", not(empty()));
    }

    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Get Users")
    @Description("Verify listing users with page query returns 200 and a non-empty data array")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetAllUsersPerPage() {
        Response response = userAPI.getUserList(1);

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data", not(empty()))
                .body("page", equalTo(1));
    }

    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Get Users")
    @Description("Verify listing specific user returns 200 and user data is not null")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSpecificUser() {
        Response response = userAPI.getUser(1);

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("data", notNullValue());
    }

    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Create User")
    @Description("Verify creating user returns 201")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        CreateUserRequest createUser = new CreateUserRequest("testName","testJob");
        Response response = userAPI.createUser(createUser);

        response.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("name", equalTo("testName"))
                .body("job",equalTo("testJob"))
                .body("id",notNullValue())
                .body("createdAt",notNullValue());
    }

    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Update User")
    @Description("Verify updating specific user returns 200")
    @Severity(SeverityLevel.CRITICAL)
    public void testUpdateUser() {
        UpdateUserRequest updateUser = new UpdateUserRequest("morpheus","zion resident");

        Response response = userAPI.updateUser(1, updateUser );

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("name", equalTo("morpheus"))
                .body("job",equalTo("zion resident"))
                .body("updatedAt",notNullValue());
    }

    @Test(groups = {"smoke","regression"})
    @Feature("User")
    @Story("Delete User")
    @Description("Verify deleting specific user returns 204")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteSpecificUser() {
        Response response = userAPI.deleteUser(1);

        response.then()
                .statusCode(204)
                .time(lessThan(ConfigReader.getLong("response.timeout.ms")));
    }
}
