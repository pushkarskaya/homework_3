package User.createUser;

import Mocks.UserMock;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.junit.Test;
import specification.HMSpecification;

public class SpecBuilderTestsDemo {

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
                .spec(HMSpecification.getRequestSpecification())
                .body(UserMock.getUserPositive(userName))
                .when().log().all()
                .post()
                .then();
    }

    public ValidatableResponse CreateUserNegative(String userName, Integer statusCode) {// Неуспешное создание пользователя
        return RestAssured
                .given()
                .spec(HMSpecification.getRequestSpecification())
                .body(UserMock.getUserNegative(userName))
                .when().log().all()
                .post()
                .then()
                .spec(HMSpecification.getResponseSpecification(statusCode))
                ;
    }


    public ValidatableResponse GetUserByName(String userName, Integer statusCode) {
        String url = "https://petstore.swagger.io/v2/user/" + userName;
        return RestAssured
                .when()
                .get(url)
                .then()
                .spec(HMSpecification.getResponseSpecification(statusCode))
                ;
    }


    @Test
    public void checkCreateUserPositive() {    // Тест Успешное создание и получение пользователя
        String userName = "GoodUser";         // Проверка соответствия ответа на post JSON схеме CreateUser.JSON
        CreateUserPositive(userName)
                .spec(validateCreateUserJsonSchema()); //Проверка json схемы схеме CreateUser.JSON
        GetUserByName(userName, 200).log().all();//Проверка, что создание пользователя успешно
    }

    @Test
    public void checkCreateUserNegative() { //Тест НЕуспешное создание пользователя
        String userName = "BadUser";
        CreateUserNegative(userName, 500)                     //Отправляем запрос на создание пользователя, в теле запроса есть ошибка
                .log().all(); // Сервер возвращает 500, если в теле запроса есть ошибка
    }                                                    // соответствия типов


    @Test
    public void checkNotExistUser() { //Тест Получение пользователя по имени (Пользователь отсутствует в БД)
        String userName = "Michail";
        GetUserByName(userName, 404)
                .log().all(); // Если пользователь отсутствует в БД, ответ сервера 404
    }

    @Test
    public void checkExistUser() {  //Тест Получение пользователя по имени (Пользователь существует в БД)
        String userName = "Olga";    // и проверка валидации ответа сервера при получении пользователя схеме JSON GetUser.JSON
        CreateUserPositive(userName) // Успешное создание пользователя
                .log().all();

        GetUserByName(userName, 200)// Получение пользователя
                .spec(validateGetUserJsonSchema()).log().all(); // Проверка валидации ответа сервера схеме JSON GetUser.JSON
    }
}

