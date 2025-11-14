/**
 * версия модели пользователя.
 * 
 * Пока что можно:
 * - Просто хранит логин, пароль и роль.
 * - Роль задаётся строкой.
 * 
 * в перспективе:
 * - Добавить ID пользователя.
 * - Реализовать безопасное хранение пароля типо хэширование.
 */

/* переопределила метод String! Пароль теперь не выводится*/
package model;

public class User {
    private String login;
    private String password;
    private UserRole role;

    public User(String login, String password, UserRole role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", role=" + role +
                '}';
    }
}
