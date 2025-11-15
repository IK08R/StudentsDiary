package ui;

import model.User;
import model.UserRole;
import model.WeekType;
import service.AuthService;
import service.ScheduleService;
import java.util.Scanner;

public class ConsoleUI {
    private final AuthService authService;
    private final ScheduleService scheduleService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(AuthService authService, ScheduleService scheduleService) {
        this.authService = authService;
        this.scheduleService = scheduleService;
    }

    public void start() {
        System.out.println("Ежедневник студента");
        if (!performLogin()) {
            System.out.println("Не удалось войти. Завершение.");
            return;
        }

        runMainMenu();
    }

    private boolean performLogin() {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Логин: ");
            String login = scanner.nextLine();
            System.out.print("Пароль: ");
            String password = scanner.nextLine();

            User user = authService.login(login, password);
            if (user != null) {
                System.out.println("Добро пожаловать, " + user.getLogin() + "!");
                return true;
            } else {
                System.out.println("Неверный логин или пароль. Попытка " + attempt + " из 3.");
            }
        }
        return false;
    }

    private void runMainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n--- Главное меню ---");
            System.out.println("1. Просмотреть расписание");
            System.out.println("2. Заметки (в разработке)");
            if (isAdminOrHeadman()) {
                System.out.println("3. Управление пользователями");
                System.out.println("0. Выйти");
                System.out.print("Выберите: ");
                int choice = getIntInput();
                switch (choice) {
                    case 1 -> showSchedule();
                    case 2 -> System.out.println("Заметки пока недоступны.");
                    case 3 -> manageUsers();
                    case 0 -> running = false;
                    default -> System.out.println("Неверный выбор.");
                }
            } else {
                System.out.println("0. Выйти");
                System.out.print("Выберите: ");
                int choice = getIntInput();
                switch (choice) {
                    case 1 -> showSchedule();
                    case 2 -> System.out.println("Заметки пока недоступны.");
                    case 0 -> running = false;
                    default -> System.out.println("Неверный выбор.");
                }
            }
        }
        scanner.close();
    }

    private boolean isAdminOrHeadman() {
        var user = authService.getCurrentUser();
        return user != null && (user.getRole() == UserRole.ADMIN || user.getRole() == UserRole.HEADMAN);
    }

    private void showSchedule() {
        System.out.println("\nРасписание:");
        System.out.println("--- Чётная неделя ---");
        scheduleService.getScheduleForWeek(WeekType.EVEN).forEach(System.out::println);
        System.out.println("\n--- Нечётная неделя ---");
        scheduleService.getScheduleForWeek(WeekType.ODD).forEach(System.out::println);
    }

    private void manageUsers() {
        System.out.println("\nСписок пользователей:");
        scheduleService.getAllUsers().forEach(System.out::println);
    }

    private int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
