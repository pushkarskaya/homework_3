package Mocks;

import dto.User;

import java.util.HashMap;
import java.util.Map;

public class UserMock {

    // Делаем приватный конструктор
    private UserMock() {
    }

    // Тут еще можно сделать Singleton. Предлагаю попробовать решить данную задачу.
    public static User getUserPositive(String userName) {
        User user = new User();
        user.setUserStatus(120l);
        user.setId(609l);
        user.setFirstName("Roman");
        user.setLastName("MMMM");
        user.setPassword("111");
        user.setEmail("aaa");
        user.setUsername(userName);
        user.setPhone("23423");
        return user;
    }

    // Можно еще передалать DTO что бы так же был User, ане HashMap через дженерики.
    public static Map getUserNegative(String userName) {
        Map notValidUserCreateMap = new HashMap();
        notValidUserCreateMap.put("userStatus", "bbbb");
        notValidUserCreateMap.put("id", "uuuuu");
        notValidUserCreateMap.put("firstName", "Roman");
        notValidUserCreateMap.put("lastName", "MMMM");
        notValidUserCreateMap.put("password", "111");
        notValidUserCreateMap.put("email", "aaa");
        notValidUserCreateMap.put("username", userName);
        notValidUserCreateMap.put("phone", "23423");
        return notValidUserCreateMap;
    }
}
