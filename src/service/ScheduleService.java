package service;

import data.ScheduleRepository;
import data.UserRepository;
import model.Lesson;
import model.User;
import model.WeekType;
import java.util.List;

public class ScheduleService {
    private final ScheduleRepository scheduleRepo = new ScheduleRepository();
    private final UserRepository userRepo = new UserRepository();

    public List<Lesson> getScheduleForWeek(WeekType weekType) {
        return scheduleRepo.getLessonsForWeek(weekType);
    }

    // пока что просто возвращает всех пользователей (для админа)
    public Iterable<User> getAllUsers() {
        return userRepo.getAllUsers();
    }
// потом добавим функцию GetScheduleForUser с userid и неделей
}
