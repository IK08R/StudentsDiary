package data;

import model.User;
import model.UserRole;
import java.util.List;

public class UserRepository {
    private final Map<String, User> users = new HashMap<>();

    public UserRepository() {// тестовые данные
        users.put("student", new User("student", "1234", UserRole.STUDENT));
        users.put("headman", new User("headman", "headman", UserRole.HEADMAN));
        users.put("admin", new User("admin", "admin", UserRole.ADMIN));
    }
    
    public User findByLogin(String login) {  //поиск по логину
        return users.get(login);
    }

    public boolean validatePassword(String login, String password) { //проверка пароля
        User user = findByLogin(login);
        return user != null && user.getPassword().equals(password);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}
