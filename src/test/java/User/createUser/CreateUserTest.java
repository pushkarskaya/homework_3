package User.createUser;

import dto.User;
import org.junit.Test;
import services.UserApi;
import java.util.logging.Logger;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {
    UserApi userApi = new UserApi();
    Logger logger;

    public void checkCreateUser(String userName) {// Создание пользователя с заданным ником
        User user = User.builder()
                .userStatus(10l)
                .email("kkk")
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
                .time(lessThan(5000l))
                .body("type", equalTo("unknown"))
                .body("message", comparesEqualTo("203"));
    }

    public void checkUserByName(String userName) { //Поиск пользователя по имени
        userApi.getUserByName(userName)
                .then()
                .log().all()
                .statusCode(200)
                .body("username", equalTo(userName));
    }

    //Тесты на создание пользователя
    @Test
    public void testCheckCreateUserStar() {//Тест Создание и проверка успешного создания пользователя с ником StarWar2021
        checkCreateUser("StarWar2021");
    }

    @Test
    public void testCheckCreateUserMoon() {//Тест Создание и проверка успешного создания пользователя с ником Moon
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
