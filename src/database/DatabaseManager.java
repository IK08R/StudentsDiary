
package database;

import java.sql.*;

// SQLite + SELECT
//Создаёт таблички и тест пользователей при 1 запуске.
public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:students_diary.db";

    // Инициализация БД при первом обращении 
    static {
        try {
            initDatabase();
        } catch (SQLException e) {
            System.err.println(" Ошибка инициализации базы данных: " + e.getMessage());
        }
    }
    // Возвращает новое подключение к бд
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    // Создаёт таблицы и добавляет тест пользователей
    private static void initDatabase() throws SQLException {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Таблица пользователей
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "login TEXT UNIQUE NOT NULL, " +
                "password TEXT NOT NULL, " +
                "role TEXT NOT NULL)"
            );

            // Таблица занятий
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS lessons (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "subject TEXT NOT NULL, " +
                "teacher TEXT, " +
                "room TEXT, " +
                "day_of_week TEXT NOT NULL, " +
                "start_time TEXT NOT NULL, " +
                "end_time TEXT NOT NULL, " +
                "week_type TEXT NOT NULL)"
            );

            // Таблица заметок
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS notes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "lesson_id INTEGER NOT NULL, " +
                "text TEXT NOT NULL)"
            );

            // Проверяем, есть ли уже пользователи
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM users");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO users (login, password, role) VALUES ('student', '1234', 'студент')");
                stmt.execute("INSERT INTO users (login, password, role) VALUES ('admin', 'admin', 'админ')");
                System.out.println(" Добавлены тестовые пользователи: student / admin");
            }
        }
    }
}