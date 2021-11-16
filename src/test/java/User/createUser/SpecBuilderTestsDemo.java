package User.createUser;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class SpecBuilderTestsDemo {
    private final static String BASE_URL = "https://petstore.swagger.io/v2/";
    private final static String BASE_PATH = "user/";


    public RequestSpecBuilder getCommonSpec() {

        RequestSpecBuilder builder = new RequestSpecBuilder();
        builder.setBaseUri(BASE_URL);
        builder.setBasePath(BASE_PATH);
        builder.setContentType(ContentType.JSON);
        return builder;
    }

    public RequestSpecification getCommonSpecPositive(String userName) { // Корректные параметры пользователя
        Map userCreateMap = new HashMap();                               // Все типы полей соответствуют JSON
        userCreateMap.put("userStatus", 120l);
        userCreateMap.put("id", 609l);
        userCreateMap.put("firstName", "Roman");
        userCreateMap.put("lastName", "MMMM");
        userCreateMap.put("password", "111");
        userCreateMap.put("email", "aaa");
        userCreateMap.put("username", userName);
        userCreateMap.put("phone", "23423");
        return getCommonSpec().setBody(userCreateMap).build();

    }

    public RequestSpecification getCommonNegative(String userName) { // НЕкорректные параметры пользователя
        Map notValidUserCreateMap = new HashMap();                   // Тип полей userStatus и id - строковые вместо числовых
        notValidUserCreateMap.put("userStatus", "bbbb");
        notValidUserCreateMap.put("id", "uuuuu");
        notValidUserCreateMap.put("firstName", "Roman");
        notValidUserCreateMap.put("lastName", "MMMM");
        notValidUserCreateMap.put("password", "111");
        notValidUserCreateMap.put("email", "aaa");
        notValidUserCreateMap.put("username", userName);
        notValidUserCreateMap.put("phone", "23423");
        return getCommonSpec().setBody(notValidUserCreateMap).build();

    }

    public ResponseSpecification checkStatusCode(Integer code) { // Проверка ожидаемого статус-кода
        return new ResponseSpecBuilder().expectStatusCode(code).build();
    }

    public ResponseSpecification validateCreateUserJsonSchema() {   // Валидация JSON схемы при создании пользователя
        return new ResponseSpecBuilder().expectBody(JsonSchemaValidator
                .matchesJsonSchemaInClasspath("schema/CreateUser.json")).build();
    }


    public ResponseSpecification validateGetUserJsonSchema() {    // Валидация JSON схемы при получении пользователя
        return new ResponseSpecBuilder().expectBody(JsonSchemaValidator
                .matchesJsonSchemaInClasspath("schema/GetUser.json")).build();
    }


    public ValidatableResponse CreateUserPositive(String userName) { //Успешное создание пользователя
        return RestAssured
                .given()
                .spec(getCommonSpecPositive(userName))
                .when().log().all()
                .post()
                .then();

    }

    public ValidatableResponse CreateUserNegative(String userName) {// Неуспешное создание пользователя
        return RestAssured
                .given()
                .spec(getCommonNegative(userName))
                .when().log().all()
                .post()
                .then();


    }


    public ValidatableResponse GetUserByName(String userName) {
        String url = "https://petstore.swagger.io/v2/user/" + userName;
        return RestAssured
                .when()
                .get(url)
                .then();
    }


    @Test
    public void checkCreateUserPositive() {    // Тест Успешное создание и получение пользователя
        String userName = "GoodUser";         // Проверка соответствия ответа на post JSON схеме CreateUser.JSON
        CreateUserPositive(userName)
                .spec(checkStatusCode(200)).log().all()
                .spec(validateCreateUserJsonSchema()); //Проверка json схемы схеме CreateUser.JSON
        GetUserByName(userName).spec(checkStatusCode(200)).log().all();//Проверка, что создание пользователя успешно
    }

    @Test
    public void checkCreateUserNegative() { //Тест НЕуспешное создание пользователя
        String userName = "BadUser";
        CreateUserNegative(userName)                     //Отправляем запрос на создание пользователя, в теле запроса есть ошибка
                .spec(checkStatusCode(500)).log().all(); // Сервер возвращает 500, если в теле запроса есть ошибка
    }                                                    // соответствия типов


    @Test
    public void checkNotExistUser() { //Тест Получение пользователя по имени (Пользователь отсутствует в БД)
        String userName = "Michail";
        GetUserByName(userName)
                .spec(checkStatusCode(404)).log().all(); // Если пользователь отсутствует в БД, ответ сервера 404
    }

    @Test
    public void checkExistUser() {  //Тест Получение пользователя по имени (Пользователь существует в БД)
        String userName = "Olga";    // и проверка валидации ответа сервера при получении пользователя схеме JSON GetUser.JSON
        CreateUserPositive(userName) // Успешное создание пользователя
                .spec(checkStatusCode(200)).log().all();
        GetUserByName(userName).spec(checkStatusCode(200)).log().all() // Получение пользователя
                .spec(validateGetUserJsonSchema()).log().all(); // Проверка валидации ответа сервера схеме JSON GetUser.JSON
    }
}

