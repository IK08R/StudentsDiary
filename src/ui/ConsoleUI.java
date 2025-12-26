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
        System.out.println("=== Ежедневник студента ===");
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
    System.out.println("\n~ Чётная неделя ~");
    printScheduleAsTable(scheduleService.getScheduleForWeek("EVEN"));

    System.out.println("\n~ Нечётная неделя ~");
    printScheduleAsTable(scheduleService.getScheduleForWeek("ODD"));
    }

    private void manageUsers() {
        System.out.println("\nСписок пользователей:");
        scheduleService.getAllUsers().forEach(System.out::println);
    private int getIntInput() {
    private void printScheduleAsTable(ArrayList<Lesson> lessons) {
    String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
    
    // Соберём пары по времени
    java.util.Set<String> timeSlots = new java.util.TreeSet<>(); // автоматически отсортирует
    for (Lesson l : lessons) {
        timeSlots.add(l.getStartTime() + "–" + l.getEndTime());
    }

    if (timeSlots.isEmpty()) {
        System.out.println("  Нет занятий.");
        return;
    }

    // Преобразуем в список для индексации
    java.util.List<String> sortedTimes = new java.util.ArrayList<>(timeSlots);
    
    // Группируем: [время][день] → предмет
    java.util.Map<String, java.util.Map<String, String>> table = new java.util.HashMap<>();
    for (String time : sortedTimes) {
        table.put(time, new java.util.HashMap<>());
        for (String day : days) {
            table.get(time).put(day, ""); // пусто по умолчанию
        }
    }

    // Заполняем таблицу данными
    for (Lesson l : lessons) {
        String timeKey = l.getStartTime() + "–" + l.getEndTime();
        String day = l.getDayOfWeek();
        String subject = l.getSubject(); // можно добавить: + " (" + l.getRoom() + ")"
        if (table.containsKey(timeKey) && table.get(timeKey).containsKey(day)) {
            table.get(timeKey).put(day, subject);
        }
    }

    // Ширина колонок
    int timeColWidth = 14;
    int dayColWidth = 16;

    // Печать заголовка
    System.out.printf("%-" + timeColWidth + "s", "Время");
    for (String day : days) {
        System.out.printf("%-" + dayColWidth + "s", day);
    }
    System.out.println();

    // Печать строк
    for (String time : sortedTimes) {
        System.out.printf("%-" + timeColWidth + "s", time);
        for (String day : days) {
            String subject = table.get(time).get(day);
            if (subject.isEmpty()) {
                System.out.printf("%-" + dayColWidth + "s", "");
            } else {
                // Обрезаем, если слишком длинно
                if (subject.length() > dayColWidth - 1) {
                    subject = subject.substring(0, dayColWidth - 2) + "…";
                }
                System.out.printf("%-" + dayColWidth + "s", subject);
            }
        }
        System.out.println();
    }
    }
    private void addLesson() {
        System.out.print("Предмет: ");
        String subject = scanner.nextLine();
        System.out.print("Преподаватель: ");
        String teacher = scanner.nextLine();
        System.out.print("Аудитория: ");
        String room = scanner.nextLine();
        System.out.print("День недели (Понедельник, Вторник...): ");
        String day = scanner.nextLine();
        System.out.print("Начало (09:00): ");
        String start = scanner.nextLine();
        System.out.print("Окончание (10:30): ");
        String end = scanner.nextLine();
        System.out.print("Тип недели (EVEN / ODD): ");
        String week = scanner.nextLine().trim().toUpperCase();

        if (!"EVEN".equals(week) && !"ODD".equals(week)) {
            System.out.println(" Ошибка: введите EVEN или ODD.");
            return;
        }
        scheduleService.addLesson(subject, teacher, room, day, start, end, week);
        System.out.println(" Занятие добавлено.");
    }

    private void manageNotes() {
        ArrayList<Lesson> lessons = scheduleService.getAllLessonsForUser();
        if (lessons.isEmpty()) {
            System.out.println("У вас нет занятий.");
            return;
        }

        System.out.println("Ваши занятия:");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson l = lessons.get(i);
            System.out.println((i + 1) + ". " + l.getSubject() + " (" + l.getDayOfWeek() + ")");
        }

        System.out.print("Выберите номер занятия: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
