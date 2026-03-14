package api;

import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import base.BaseTest;
import utils.Constants;


public class ResourceAPI extends BaseTest {
    public Response getResource(int id)
    {
        return given(requestSpec)
            .when()
            .get(Constants.RESOURCE_BY_ID,id);
    }

    public Response getResourceList()
    {
        return given(requestSpec)
            .when()
            .get(Constants.RESOURCES);
    }

    public Response getResourceList(int page)
    {
        return given(requestSpec)
            .queryParam("page",page)
            .when()
            .get(Constants.RESOURCES);
    }
    
}
