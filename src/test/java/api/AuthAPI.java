package api;

import static io.restassured.RestAssured.given;

import models.RegisterRequest;
import utils.Constants;
import base.BaseTest;
import io.restassured.response.Response;
import models.LoginRequest;

public class AuthAPI extends BaseTest {
    public Response register(RegisterRequest request) 
    {
        return given(requestSpec)
            .body(request)
            .when()
            .post(Constants.REGISTER);
    }
    
    public Response login(LoginRequest request)
    {
        return given(requestSpec)
            .body(request)
            .when()
            .post(Constants.LOGIN);
    }
}
