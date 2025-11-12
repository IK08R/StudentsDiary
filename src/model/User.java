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

package model;

public class User {
    private String login;
    private String password;
    private String role; // студент, староста, админ

    public User(String login, String password, String role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }
    
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    
}
