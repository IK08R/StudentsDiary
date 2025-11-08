package model;

public class User {
    private String login;
    private String password;
    private String role; // "student", "headman", "admin"

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    // Геттеры
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // TODO: добавить equals(), hashCode(), toString() позже
}
