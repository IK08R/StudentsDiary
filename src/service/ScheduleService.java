package service;

import model.Lesson;
import database.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;

// Работа с расписанием: добавление, просмотр.
public class ScheduleService {
    private final AuthService authService;

    public ScheduleService(AuthService authService) {
        this.authService = authService;
    }

    public ArrayList<Lesson> getScheduleForWeek(String weekType) {
        ArrayList<Lesson> lessons = new ArrayList<>();
        int userId = authService.getCurrentUser().getId();
        String sql = "SELECT * FROM lessons WHERE user_id = ? AND week_type = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, weekType);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lessons.add(new Lesson(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("subject"),
                    rs.getString("teacher"),
                    rs.getString("room"),
                    rs.getString("day_of_week"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getString("week_type")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки расписания: " + e.getMessage());
        }
        return lessons;
    }

    public void addLesson(String subject, String teacher, String room,
                          String dayOfWeek, String startTime, String endTime, String weekType) {
        int userId = authService.getCurrentUser().getId();
        String sql = "INSERT INTO lessons (user_id, subject, teacher, room, day_of_week, start_time, end_time, week_type) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, subject);
            stmt.setString(3, teacher);
            stmt.setString(4, room);
            stmt.setString(5, dayOfWeek);
            stmt.setString(6, startTime);
            stmt.setString(7, endTime);
            stmt.setString(8, weekType);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка добавления занятия: " + e.getMessage());
        }
    }

    public ArrayList<Lesson> getAllLessonsForUser() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        int userId = authService.getCurrentUser().getId();
        String sql = "SELECT * FROM lessons WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lessons.add(new Lesson(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("subject"),
                    rs.getString("teacher"),
                    rs.getString("room"),
                    rs.getString("day_of_week"),
                    rs.getString("start_time"),
                    rs.getString("end_time"),
                    rs.getString("week_type")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        return lessons;
    }
}