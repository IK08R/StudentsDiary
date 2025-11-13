/**
 * версия занятия (пары).
 * 
 * Пока что можно:
 * - Хранит просто базовые поля.
 * - Пока нет привязки к пользователю или неделе.
 * 
 * в перспективе:
 * - Добавить WeekType (чётная/нечётная неделя).
 * - Добавить userId — для персонализации расписания.
 */



package model;

public class Lesson {
    private String subject;
    private String teacher;
    private String room;
    private String dayOfWeek; // "Понедельник", "Вторник"...
    private String startTime;
    private String endTime;
    private WeekType weekType; // EVEN или ODD

    public Lesson(String subject, String teacher, String room, String dayOfWeek,
                  String startTime, String endTime, WeekType weekType) {
        this.subject = subject;
        this.teacher = teacher;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.weekType = weekType;
    }

    // Геттеры
    public String getSubject() { return subject; }
    public String getTeacher() { return teacher; }
    public String getRoom() { return room; }
    public String getDayOfWeek() { return dayOfWeek; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public WeekType getWeekType() { return weekType; }

    @Override
    public String toString() {
        return String.format("%s | %s–%s | %s (%s) | %s неделя",
                subject, startTime, endTime, room, teacher, weekType == WeekType.EVEN ? "Чётная" : "Нечётная");
    }
}

