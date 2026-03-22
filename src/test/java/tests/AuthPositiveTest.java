package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import models.LoginRequest;
import models.RegisterRequest;

import static org.hamcrest.Matchers.emptyString;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.AuthAPI;

@Epic("Auth API")
public class AuthPositiveTest extends BaseTest {
    private AuthAPI authAPI;

    @BeforeClass
    public void setUp() {
        authAPI = new AuthAPI();
    }

    @Test(groups = {"smoke","regression"})
    @Feature("Login User")
    @Story("Login Valid User")
    @Description("Verify login user with valid credentials returns 200 and a token field in the body")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginUser() {
        LoginRequest login = new LoginRequest("eve.holt@reqres.in","cityslicka");
        Response response = authAPI.login(login);

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body("token",not(emptyString()));
    }

    @Test(groups = {"smoke","regression"})
    @Feature("Register User")
    @Story("Register Valid User")
    @Description("Verify register user with valid credentials returns 201 with both id and token fields")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegisterUser() {
        RegisterRequest register = new RegisterRequest("eve.holt@reqres.in","pistol");
        Response response = authAPI.register(register);

        response.then()
                .spec(responseSpec)
                .statusCode(201)
                .body("id",notNullValue())
                .body("token",not(emptyString()));
    }

}
