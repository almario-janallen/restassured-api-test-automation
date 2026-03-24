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

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import api.AuthAPI;
import utils.DataProviders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyString;

@Epic("Auth API")
public class AuthDataDrivenTest extends BaseTest {
    private AuthAPI authAPI;

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        authAPI = new AuthAPI();
    }

    // -------------------------------------------------------------------------
    // POST /login (success)  —  driven by login_valid.csv
    // Columns: email, password, expectedStatusCode
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},dataProvider = "loginValidData",dataProviderClass = DataProviders.class)
    @Feature("Login User")
    @Story("Login with Valid Credentials - Data Driven")
    @Description("Login with valid credentials from CSV")
    @Severity(SeverityLevel.CRITICAL)
    public void testLoginValidDataDriven(String email, String password, String expectedStatusCode) {
        LoginRequest request = buildLoginRequest(email, password);
        Response response = authAPI.login(request);

        response.then()
            .spec(responseSpec)
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("token", not(emptyString()));
    }

    // -------------------------------------------------------------------------
    // POST /login (failure)  —  driven by login_invalid.csv
    // Columns: email, password, expectedStatusCode, expectedError
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},dataProvider = "loginInvalidData",dataProviderClass = DataProviders.class)
    @Feature("Login User")
    @Story("Login with Invalid Credentials - Data Driven")
    @Description("Login User with invalid credentials from CSV")
    @Severity(SeverityLevel.NORMAL)
    public void testLoginInvalidDataDriven(String email, String password, String expectedStatusCode, String expectedError) {
        LoginRequest request = buildLoginRequest(email, password);
        Response response = authAPI.login(request);

        response.then()
            .spec(responseSpec)
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("error", equalTo(expectedError));
    }

    // -------------------------------------------------------------------------
    // POST /register (success)  —  driven by register_valid.csv
    // Columns: email, password, expectedStatusCode
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},dataProvider = "registerValidData",dataProviderClass = DataProviders.class)
    @Feature("Register User")
    @Story("Register with Valid Credentials - Data Driven")
    @Description("Register User with Valid credentials from CSV")
    @Severity(SeverityLevel.CRITICAL)
    public void testRegisterValidDataDriven(String email, String password, String expectedStatusCode) {
        RegisterRequest request = buildRegisterRequest(email, password);
        Response response = authAPI.register(request);

        response.then()
                .spec(responseSpec)
                .statusCode(Integer.parseInt(expectedStatusCode))
                .body("id", notNullValue())
                .body("token", not(emptyString()));
    }

    // -------------------------------------------------------------------------
    // POST /register (failure)  —  driven by register_invalid.csv
    // Columns: email, password, expectedStatusCode, expectedError
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},dataProvider = "registerInvalidData",dataProviderClass = DataProviders.class)
    @Feature("Register User")
    @Story("Register with Invalid Credentials - Data Driven")
    @Description("Register User with Invalid credentials from CSV")
    @Severity(SeverityLevel.NORMAL)
    public void testRegisterInvalidDataDriven(String email, String password,String expectedStatusCode, String expectedError) {
        RegisterRequest request = buildRegisterRequest(email, password);
        Response response = authAPI.register(request);

        response.then()
                .spec(responseSpec)
                .statusCode(Integer.parseInt(expectedStatusCode))
                .body("error", equalTo(expectedError));
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