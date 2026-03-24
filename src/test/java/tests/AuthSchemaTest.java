package tests;

import utils.ConfigReader;
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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.AuthAPI;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic("Auth API")
public class AuthSchemaTest extends BaseTest {
    private AuthAPI authAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        authAPI = new AuthAPI();
    }

    // -------------------------------------------------------------------------
    // POST /login (success)  →  auth_login_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Login User")
    @Story("Login Success Schema")
    @Description("Validate response schema for POST /login (valid credentials)")
    @Severity(SeverityLevel.NORMAL)
    public void validateLoginSuccessSchema() {
        LoginRequest request = buildLoginRequest(ConfigReader.get("login.username"), ConfigReader.get("login.password"));
        Response response = authAPI.login(request);

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/auth_login_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /login (failure)  →  auth_error_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Login User")
    @Story("Login Error Schema")
    @Description("Validate error response schema for POST /login (missing password)")
    @Severity(SeverityLevel.NORMAL)
    public void validateLoginErrorSchema() {
        LoginRequest request = buildLoginRequest(ConfigReader.get("login.username"), "");
        Response response = authAPI.login(request);

        response.then()
                .spec(responseSpec)    
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/auth_error_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /register (success)  →  auth_register_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Register User")
    @Story("Register Success Schema")
    @Description("Validate response schema for POST /register (valid payload)")
    @Severity(SeverityLevel.NORMAL)
    public void validateRegisterSuccessSchema() {
        RegisterRequest request = buildRegisterRequest(ConfigReader.get("register.username"), ConfigReader.get("register.password"));
        Response response = authAPI.register(request);

        response.then()
                .spec(responseSpec)
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/auth_register_schema.json"));
    }

    // -------------------------------------------------------------------------
    // POST /register (failure)  →  auth_error_schema.json
    // -------------------------------------------------------------------------

    @Test(groups = {"schema", "regression"})
    @Feature("Register User")
    @Story("Register Error Schema")
    @Description("Validate error response schema for POST /register (missing password)")
    @Severity(SeverityLevel.NORMAL)
    public void validateRegisterErrorSchema() {
        RegisterRequest request = buildRegisterRequest(ConfigReader.get("register.username"),"");
        Response response = authAPI.register(request);

        response.then()
                .spec(responseSpec)
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/auth_error_schema.json"));
    }

    // -------------------------------------------------------------------------
    // Helper — builds a JSON body with only the fields that are non-empty.
    // This simulates missing fields without sending empty strings to the API.
    // -------------------------------------------------------------------------

    private LoginRequest buildLoginRequest(String email, String password) {
        LoginRequest request = new LoginRequest();

        if (email != null && !email.trim().isEmpty()) {
            request.setEmail(email);
        }
        if (password != null && !password.trim().isEmpty()) {
            request.setPassword(password);
        }
    
        return request;
    }

    private RegisterRequest buildRegisterRequest(String email, String password) {
        RegisterRequest request = new RegisterRequest();

        if (email != null && !email.trim().isEmpty()) {
            request.setEmail(email);
        }
        if (password != null && !password.trim().isEmpty()) {
            request.setPassword(password);
        }
    
        return request;
    }
}