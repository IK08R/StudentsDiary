import service.AuthService;
import service.ScheduleService;
import ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        AuthService authService = new AuthService();
        ScheduleService scheduleService = new ScheduleService();
        ConsoleUI ui = new ConsoleUI(authService, scheduleService);
        ui.start();
    }
}
