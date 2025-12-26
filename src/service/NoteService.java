package service;

import model.Note;
import database.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;

//Работа с заметками: добавление, просмотр.
public class NoteService {
    private final AuthService authService;

    public NoteService(AuthService authService) {
        this.authService = authService;
    }

    public ArrayList<Note> getNotesForLesson(int lessonId) {
        ArrayList<Note> notes = new ArrayList<>();
        int userId = authService.getCurrentUser().getId();
        String sql = "SELECT * FROM notes WHERE user_id = ? AND lesson_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, lessonId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(new Note(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getInt("lesson_id"),
                    rs.getString("text")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка загрузки заметок: " + e.getMessage());
        }
        return notes;
    }

    public void addNote(int lessonId, String text) {
        int userId = authService.getCurrentUser().getId();
        String sql = "INSERT INTO notes (user_id, lesson_id, text) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, lessonId);
            stmt.setString(3, text);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка добавления заметки: " + e.getMessage());
        }
    }
}