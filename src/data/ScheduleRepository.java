package data;

import model.Lesson;
import model.WeekType;
import java.util.*;
public class ScheduleRepository {
    private final List<Lesson> lessons = new ArrayList<>();

    public ScheduleRepository() { // Пример данных
        lessons.add(new Lesson("Математика", "Иванов И.И.", "А-201", "Понедельник", "09:00", "10:30", WeekType.ODD));
        lessons.add(new Lesson("Программирование", "Петров П.П.", "Б-305", "Понедельник", "10:45", "12:15", WeekType.EVEN));
        lessons.add(new Lesson("Физика", "Сидоров С.С.", "В-101", "Вторник", "09:00", "10:30", WeekType.BOTH)); // WeekType.BOTH пока не поддрживается, но в будущем реализуем
    }

    public List<Lesson> getLessons() {
        return new ArrayList<>(lessons);
    }

    // Пока упрощённая функция расписания
    public List<Lesson> getLessonsForWeek(WeekType weekType) {
        return lessons.stream()
                .filter(lesson -> lesson.getWeekType() == weekType)
                .toList();
    }
}
