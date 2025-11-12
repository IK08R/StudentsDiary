package data;

import model.User;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        // Тестовые данные
        users.add(new User("student", "1234", "student"));
        users.add(new User("admin", "admin", "admin"));
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users); // защита от изменения извне
    }

    // добавить метод findByLogin(String login)
}
