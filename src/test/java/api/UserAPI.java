package api;

import static io.restassured.RestAssured.given;
import base.BaseTest;
import io.restassured.response.Response;
import models.CreateUserRequest;
import models.UpdateUserRequest;
import utils.Constants;

public class UserAPI extends BaseTest {

    public Response createUser(CreateUserRequest createUserRequest) {
        return given(requestSpec)
            .body(createUserRequest)
            .when()
            .post(Constants.USERS);
    }

    public Response updateUser(int id, UpdateUserRequest updateUserRequest) {
        return given(requestSpec)
            .body(updateUserRequest)
            .when()
            .put(Constants.USER_BY_ID,id);
    }

    public Response deleteUser(int id) {
        return given(requestSpec)
            .when()
            .delete(Constants.USER_BY_ID,id);
    }

    public Response getUser(int id) {
        return given(requestSpec)
            .when()
            .get(Constants.USER_BY_ID,id);
    }

    public Response getUserList() {
        return given(requestSpec)
            .when()
            .get(Constants.USERS);
    }
    public Response getUserList(int page) {
        return given(requestSpec)
            .queryParam("page", page)
            .when()
            .get(Constants.USERS);
    }
    
}
