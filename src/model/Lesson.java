package model;

// Модель пары
public class Lesson {
    private int id;
    private int userId;          // чей урок
    private String subject;      // предмет
    private String teacher;      // преподаватель
    private String room;         // аудитория
    private String dayOfWeek;    // день недели
    private String startTime;    
    private String endTime;      
    private String weekType;     // чет и нечет
    private String noteText;

    public Lesson(int id, int userId, String subject, String teacher, String room,
                  String dayOfWeek, String startTime, String endTime, String weekType) {
        this.id = id;
        this.userId = userId;
        this.subject = subject;
        this.teacher = teacher;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekType = weekType;
    }

    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getSubject() { return subject; }
    public String getTeacher() { return teacher; }
    public String getRoom() { return room; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getWeekType() { return weekType; }
    public String getNoteText() {
        return noteText;
    }

    // сеттер 
    public void setNoteText(String noteText) {
    this.noteText = noteText;
    }
}