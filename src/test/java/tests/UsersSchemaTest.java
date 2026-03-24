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
import models.UpdateUserRequest;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.UserAPI;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("User API")
public class UsersSchemaTest extends BaseTest {
    private UserAPI userAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        userAPI = new UserAPI();
    }
    // -------------------------------------------------------------------------
    // GET /users  →  user_list_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Get User")
    @Story("Get All Users Schema")
    @Description("Validate response schema for GET /users (list)")
    @Severity(SeverityLevel.NORMAL)
    public void validateUserListSchema() {
        Response response = userAPI.getUserList();

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/user_list_schema.json"));
    }

    // -------------------------------------------------------------------------
    // GET /users/{id}  →  user_single_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Get User")
    @Story("Get Single User Schema")
    @Description("Validate response schema for GET /users/{id} (single user)")
    @Severity(SeverityLevel.NORMAL)
    public void validateUserSingleSchema() {
        Response response = userAPI.getUser(1);

        response.then()
            .spec(responseSpec)
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user_single_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /users  →  user_create_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Create User")
    @Story("Create User Schema")
    @Description("Validate response schema for POST /users (create user)")
    @Severity(SeverityLevel.NORMAL)
    public void validateUserCreateSchema() {
        CreateUserRequest createUser = new CreateUserRequest("testName","testJob");
        Response response = userAPI.createUser(createUser);

        response.then()
                .spec(responseSpec)
                .statusCode(201)
            .body(matchesJsonSchemaInClasspath("schemas/user_create_schema.json"));
    }

    // -------------------------------------------------------------------------
    // PUT /users/{id}  →  user_update_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Update User")
    @Story("Update User Schema")
    @Description("Validate response schema for PUT /users/{id} (full update)")
    @Severity(SeverityLevel.NORMAL)
    public void validateUserPutSchema() {
        UpdateUserRequest updateUser = new UpdateUserRequest("morpheus","zion resident");

        Response response = userAPI.updateUser(1, updateUser );

        response.then()
                .spec(responseSpec)
                .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/user_update_schema.json"));
    }
}