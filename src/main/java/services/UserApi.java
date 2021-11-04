package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;


public class UserApi {
    private final static String BASE_URL = "https://petstore.swagger.io/v2";
    private final static String BASE_PATH = "/user";
    private static String userName = "mimimi";
    private RequestSpecification spec;


    public UserApi() {
        spec =
                given()
                        .contentType(ContentType.JSON)
                        .baseUri(BASE_URL)
                        .basePath(BASE_PATH);

    }

    public Response getUser(User user) {
        return given(spec)
                .body(user)
                .when().log().all()
                .post();
    }

    public Response getUserByName(String userName) {
        String BASE_URL1 = "https://petstore.swagger.io/v2/user/";
        String BASE_PATH1 = "/" + userName;
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL1)
                .basePath(BASE_PATH1)
                .get();

    }

}
