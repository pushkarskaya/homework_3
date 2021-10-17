package User.createUser;

import dto.User;
import org.junit.jupiter.api.Test;
import services.UserApi;

public class CreateUserTest {
    UserApi userApi=new UserApi();

    @Test
public  void checkCreateUser(){
User user=User.builder()
        .userStatus(10l)
        .email("kkk")
        .id(202l)
        .firstName("Geits")
        .lastName("Bill")
        .password("password")
        .username("username")
        .phone("phone")
        .build();
       userApi.getUser(user)
               .then()
               .statusCode(200);

    }
}
