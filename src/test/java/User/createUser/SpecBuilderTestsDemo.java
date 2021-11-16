package User.createUser;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class RequestSpecDemo {
    private final static String BASE_URL = "https://petstore.swagger.io/v2/";
    private final static String BASE_PATH="user/";

    public RequestSpecification getCommonSpec() {

        Map userCreateMap = new HashMap();
        userCreateMap.put("userStatus", 120l);
        userCreateMap.put("id", 609l);
        userCreateMap.put("firstName", "Roman");
        userCreateMap.put("lastName", "MMMM");
        userCreateMap.put("password", "111");
        userCreateMap.put("email", "aaa");
        userCreateMap.put("username", "Roman");
        userCreateMap.put("phone", "23423");


        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URL);
        builder.setBasePath(BASE_PATH);
        builder.setContentType(ContentType.JSON);
        builder.setBody(userCreateMap);
        RequestSpecification requestSpec = builder.build();
        return requestSpec;
    }

    public ResponseSpecification commonSpec(){
        ResponseSpecBuilder builder=new ResponseSpecBuilder();
        builder.expectStatusCode(200);
        ResponseSpecification responseSpec= builder.build();
        return responseSpec;
    }

    @Test
    public void testCreateUser() {
        Response response = RestAssured
                .given()
                .spec(getCommonSpec())
                .when().log().all()
                .post();
        response.getBody().prettyPrint();
    }

    @Test
    public void testGetUser(){
        String url="https://petstore.swagger.io/v2/user/Roman";
        RestAssured
                .when()
                .get(url)
                .then().log().all()
                .spec(commonSpec()).log().all();
    }
}
