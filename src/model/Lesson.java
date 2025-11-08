package model;

public class Lesson {
    private String subject;
    private String teacher;
    private String room;
    private String dayOfWeek; // н-р "Понедельник"
    private String startTime;  // "09:00"
    private String endTime;    // "10:30"

    public Lesson(String subject, String teacher, String room, String dayOfWeek, String startTime, String endTime) {
        this.subject = subject;
        this.teacher = teacher;
        this.room = room;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; }
    
}
