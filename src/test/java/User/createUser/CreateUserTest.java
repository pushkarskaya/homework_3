package User.createUser;

import dto.UserOut;
import dto.User;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import services.UserApi;
import java.util.logging.Logger;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {
    UserApi userApi = new UserApi();
    Logger logger;

    public void checkCreateUser(String userName) {// Создание пользователя с заданным ником
        User user = User.builder()                // Валидация ответа statusCode=200
                .userStatus(10l)                  //                  type="unknown"
                .email("kkk")                     //                  message="203"
                .id(203l)
                .firstName("Geits")
                .lastName("Bill")
                .password("password")
                .username(userName)
                .phone("phone")
                .build();
        userApi.getUser(user)
                .then()
                .log().all()
                .statusCode(200)
                .body("type", equalTo("unknown"))
                .body("message", comparesEqualTo(203));
    }

    public void checkUserByName(String userName) { //Получение пользователя по имени
        userApi.getUserByName(userName)
                .then()
                .log().all()
                .statusCode(200)
                .body("username", equalTo(userName));
    }

    @Test
    public void checkCreateUserWithAssert() {// Создание пользователя, проверка c помощью ассерта,
        User user = User.builder()           //что ответ от сервера 200
                .userStatus(10l)
                .email("kkk")
                .id(203l)
                .firstName("Lara")
                .lastName("Fabian")
                .password("password")
                .username("userForCheckTime")
                .phone("8-976-098-87-09")
                .build();
        Response response = userApi.getUser(user);
        UserOut res = response.then().extract().body().as(UserOut.class);//Преобразование ответа сервера к классу CreateUserOut
        Assert.assertEquals("200", res.getCode().toString());
    }

@Test
    public void checkCreateUserTime(){// Создание пользователя
    User user = User.builder()                // Проверка, что время ответа сервера при создании
            .userStatus(10l)                  // пользователя не превышает 4 мс
            .email("kkk")
            .id(203l)
            .firstName("Ayyyyy")
            .lastName("Woooo")
            .password("password1")
            .username("Varvara")
            .phone("phone")
            .build();
            userApi.getUser(user)
                    .then()
                    .time(lessThan(4000L));

}
@Test
public void checkCreateUserSchema() {// Создание пользователя
        User user = User.builder()                // Валидация ответа по json схеме
                .userStatus(10l)                  // Проверка наличия обязательных полей ответа
                .email("kkk")                     //Позитивный тест
                .id(203l)
                .firstName("Geits")
                .lastName("Bill")
                .password("password")
                .username("Voyager")
                .phone("phone")
                .build();
        userApi.getUser(user)
                .then()
                .statusCode(200)
                .body("type", equalTo("unknown"))
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUser.json"));

    }

    @Test
    public void checkCreateUserSchemaNegative() {// Создание пользователя
        User user = User.builder()                // Валидация ответа по json схеме
                .userStatus(10l)                  // Проверка наличия обязательных полей ответа
                .email("kkk")                     //Негативный тест, вставили несуществующее в ответе поле typenone
                .id(203l)
                .firstName("Geits")
                .lastName("Bill")
                .password("password")
                .username("Voyager")
                .phone("phone")
                .build();
        userApi.getUser(user)
                .then()
                .statusCode(200)
                .body("type", equalTo("unknown"))
                .log().all()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateUserNegative.json"));

    }

    //Тесты на создание пользователя
    @Test
    public void testCheckCreateUserStar() {//Тест Создание и проверка успешного создания пользователя с ником StarWar2021
        checkCreateUser("StarWar2021");
    }

    @Test
    public void testCheckCreateUserMoon() {//Тест Сна валидность json схемы
        checkCreateUser("Moon");

    }

    //Тесты на получение пользователя
    @Test
    public void testExistUser() { //Тест Получение пользователя по имени (пользователь есть в базе данных)
        checkUserByName("user1");
    }

    @Test
    public void testNotExistUser() { //Тест Получение пользователя по имени (пользователь отсутствует в базе данных)
        checkUserByName("kkk");
        logger.info("Пользователь не найден");
    }


}
