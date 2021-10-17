package services;

import dto.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class UserApi {
    private final static String BASE_URL="petstore.swagger.io/v2";
    private final static String BASE_PATH="/user";

    public Response getUser(User user){
        return   given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .basePath(BASE_PATH)
                .body(user)
                .when()
                .post();

    }



}
