package model;

// Модель пользователя: логин, пароль, роль.
public class User {
    private int id;
    private String login;
    private String password;
    private String role; // "студент" или "админ"

    public User(int id, String login, String password, String role) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Геттеры — нужны, чтобы другие классы могли читать данные
    public int getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}