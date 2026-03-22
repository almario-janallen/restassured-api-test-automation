package tests;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.AuthAPI;
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

import static org.hamcrest.Matchers.equalTo;

@Epic("Auth API")
public class AuthNegativeTest extends BaseTest {
    private AuthAPI authAPI;

    @BeforeClass
    public void setUp() {
        authAPI = new AuthAPI();
    }

    @Test(groups = {"regression"})
    @Feature("Login User")
    @Story("Login Unregistered User")
    @Description("Verify login user with unregistered user returns 400 and an error field in the body")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginUnregisteredUser() {
        LoginRequest login = new LoginRequest("testemail","password123");
        Response response = authAPI.login(login);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error",equalTo("user not found"));
                
    }

    @Test(groups = {"regression"})
    @Feature("Login User")
    @Story("Login User with Missing Email")
    @Description("Verify login user with missing email returns 400 and an error field in the body")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginMissingEmail() {
        LoginRequest login = new LoginRequest();
        login.setPassword("password123");
        Response response = authAPI.login(login);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error",equalTo("Missing email or username"));
    }

    @Test(groups = {"regression"})
    @Feature("Login User")
    @Story("Login User with Missing Password")
    @Description("Verify login user with missing password returns 400 and an error field in the body")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginMissingPassword() {
        LoginRequest login = new LoginRequest();
        login.setEmail("eve.holt@reqres.in");
        Response response = authAPI.login(login);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error",equalTo("Missing password"));
    }

    @Test(groups = {"regression"})
    @Feature("Register User")
    @Story("Register User with Missing Password")
    @Description("Verify register user with missing password returns 400 and an error field in the body")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterMissingPassword() {
        RegisterRequest register = new RegisterRequest();
        register.setEmail("eve.holt@reqres.in");
        Response response = authAPI.register(register);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error",equalTo("Missing password"));
    }

    @Test(groups = {"regression"})
    @Feature("Register User")
    @Story("Register User with Missing Email")
    @Description("Verify register user with missing email returns 400 and an error field in the body")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterMissingEmail() {
        RegisterRequest register = new RegisterRequest();
        register.setPassword("password123");
        Response response = authAPI.register(register);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body("error",equalTo("Missing email or username"));
    }
}
