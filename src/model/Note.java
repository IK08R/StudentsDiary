package model;

// Модель заметки — привязана к пользователю и занятию.
public class Note {
    private int id;
    private int userId;
    private int lessonId;
    private String text;

    public Note(int id, int userId, int lessonId, String text) {
        this.id = id;
        this.userId = userId;
        this.lessonId = lessonId;
        this.text = text;
    }
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public int getLessonId() { return lessonId; }
    public String getText() { return text; }
}