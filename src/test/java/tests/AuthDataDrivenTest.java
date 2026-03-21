package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import utils.Constants;
import utils.DataProviders;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.emptyString;

public class AuthDataDrivenTest extends BaseTest {

    // -------------------------------------------------------------------------
    // POST /login (success)  —  driven by login_valid.csv
    // Columns: email, password, expectedStatusCode
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},
          dataProvider = "loginValidData",
          dataProviderClass = DataProviders.class,
          description = "Login with valid credentials from CSV")
    public void testLoginValidDataDriven(String email, String password, String expectedStatusCode) {
        String requestBody = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.LOGIN)
        .then()
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("token", not(emptyString()));
    }

    // -------------------------------------------------------------------------
    // POST /login (failure)  —  driven by login_invalid.csv
    // Columns: email, password, expectedStatusCode, expectedError
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},
          dataProvider = "loginInvalidData",
          dataProviderClass = DataProviders.class,
          description = "Login with invalid credentials from CSV")
    public void testLoginInvalidDataDriven(String email, String password,
                                           String expectedStatusCode, String expectedError) {
        String requestBody = buildPartialAuthBody(email, password);

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.LOGIN)
        .then()
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("error", equalTo(expectedError));
    }

    // -------------------------------------------------------------------------
    // POST /register (success)  —  driven by register_valid.csv
    // Columns: email, password, expectedStatusCode
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},
          dataProvider = "registerValidData",
          dataProviderClass = DataProviders.class,
          description = "Register with valid credentials from CSV")
    public void testRegisterValidDataDriven(String email, String password, String expectedStatusCode) {
        String requestBody = String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.REGISTER)
        .then()
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("id", notNullValue())
            .body("token", not(emptyString()));
    }

    // -------------------------------------------------------------------------
    // POST /register (failure)  —  driven by register_invalid.csv
    // Columns: email, password, expectedStatusCode, expectedError
    // -------------------------------------------------------------------------

    @Test(groups = {"datadriven", "regression"},
          dataProvider = "registerInvalidData",
          dataProviderClass = DataProviders.class,
          description = "Register with invalid credentials from CSV")
    public void testRegisterInvalidDataDriven(String email, String password,
                                              String expectedStatusCode, String expectedError) {
        String requestBody = buildPartialAuthBody(email, password);

        given()
            .spec(requestSpec)
            .body(requestBody)
        .when()
            .post(Constants.REGISTER)
        .then()
            .statusCode(Integer.parseInt(expectedStatusCode))
            .body("error", equalTo(expectedError));
    }

    // -------------------------------------------------------------------------
    // Helper — builds a JSON body with only the fields that are non-empty.
    // This simulates missing fields without sending empty strings to the API.
    // -------------------------------------------------------------------------

    private String buildPartialAuthBody(String email, String password) {
        boolean hasEmail    = email    != null && !email.trim().isEmpty();
        boolean hasPassword = password != null && !password.trim().isEmpty();

        if (hasEmail && hasPassword) {
            return String.format("{ \"email\": \"%s\", \"password\": \"%s\" }", email, password);
        } else if (hasEmail) {
            return String.format("{ \"email\": \"%s\" }", email);
        } else if (hasPassword) {
            return String.format("{ \"password\": \"%s\" }", password);
        } else {
            return "{}";
        }
    }
}