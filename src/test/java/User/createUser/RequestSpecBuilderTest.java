package User.createUser;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;

public class RequestSpecBuilderTest{
    private final static String BASE_URL = "https://petstore.swagger.io/v2/user/";

    @BeforeClass
    public static void setUp(){
       // UserApi userApi = new UserApi();
    }

    @Test
    public void createUserWithSpecBuilder() { //Тест на успешное создание пользователя
                                            // с использованием RequestSpecBuilder
            RequestSpecBuilder builder = new RequestSpecBuilder();

            HashMap queryMap = new HashMap<>(); // Создаю queryMap - это параметры пользователя, которого
            queryMap.put("userStatus", 120l);// хочу добавить в базу
            queryMap.put("id", 609l);
            queryMap.put("firstName", "Elena");
            queryMap.put("lastName", "MMMM");
            queryMap.put("password", "111");
            queryMap.put("email", "aaa");
            queryMap.put("username", "Lalala");
            queryMap.put("phone", "23423");

            RequestSpecification requestSpec = builder.setBaseUri(BASE_URL)
                    .setContentType(ContentType.JSON)
                                                        .setBody(queryMap).build(); //В тело запроса кладу queryMap

            RestAssured.given(requestSpec).log().all().when().post().then(). //Вызов метода post
                    statusCode(200);
        }


}