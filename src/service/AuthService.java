package service;

import model.User;
import database.DatabaseManager;
import java.sql.*;

// Сервис авторизации, вся правда о текущем пользователе
public class AuthService {
    private User currentUser = null; //потому что изначально никто не вошел

    public User login(String login, String password) {
        String sql = "SELECT id, login, password, role FROM users WHERE login = ? AND password = ?";
        try (Connection conn = DatabaseManager.getConnection();//специальный метод по типу выполни код
             PreparedStatement stmt = conn.prepareStatement(sql)) { //а если не получится, то обработай ошибку
            stmt.setString(1, login);   //параметры
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                currentUser = new User(
                    rs.getInt("id"),
                    rs.getString("login"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                return currentUser;
            }
        } catch (SQLException e) {
            System.err.println("Ошибка входа: " + e.getMessage());
        }
        return null;
    }
    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return currentUser != null && "админ".equals(currentUser.getRole());
    }
}