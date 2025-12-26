import service.AuthService;
import service.ScheduleService;
import service.NoteService;
import ui.ConsoleUI;

//Точка входа в прогу.

public class Main {
    public static void main(String[] args) {
        AuthService auth = new AuthService(); //Создается проект сервиса авторризации
        ScheduleService schedule = new ScheduleService(auth); //чтоб знать кто вошел в систему
        NoteService notes = new NoteService(auth);
        ConsoleUI ui = new ConsoleUI(auth, schedule, notes);// + поля расписания и заметок
        ui.start();
    }
}