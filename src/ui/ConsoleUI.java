package ui;

import service.AuthService;
import service.ScheduleService;
import service.NoteService;
import model.Lesson;
import model.Note;
import java.util.ArrayList;
import java.util.Scanner;

// Консольный интерфейс
public class ConsoleUI {
    private final AuthService authService;
    private final ScheduleService scheduleService;
    private final NoteService noteService;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleUI(AuthService authService, ScheduleService scheduleService, NoteService noteService) {
        this.authService = authService;
        this.scheduleService = scheduleService;
        this.noteService = noteService;
    }

    public void start() {
        System.out.println("=== Ежедневник студента ===");
        if (!performLogin()) {
            System.out.println(" Не удалось войти. Программа завершена.");
            return;
        }
        mainMenu();
    }

    private boolean performLogin() {
        for (int i = 0; i < 3; i++) {
            System.out.print("Логин: ");
            String login = scanner.nextLine();
            System.out.print("Пароль: ");
            String password = scanner.nextLine();

            if (authService.login(login, password) != null) {
                System.out.println(" Приветствуем, " + login);
                return true;
            } else {
                System.out.println(" Неверный логин или пароль. Попыток осталось: " + (2 - i));
            }
        }
        return false;
    }

    private void mainMenu() {
        while (true) {
            System.out.println("\n~~~ Меню ~~~");
            System.out.println("1. Просмотреть расписание");
            System.out.println("2. Добавить занятие");
            System.out.println("3. Заметки к занятиям");
            if (authService.isAdmin()) {
                System.out.println("4. (Админка — функции не реализованы)");
            }
            System.out.println("0. Выйти");
            System.out.print("Ваш выбор: ");

            String choice = scanner.nextLine();
            if ("1".equals(choice)) {
                showSchedule();
            } else if ("2".equals(choice)) {
                addLesson();
            } else if ("3".equals(choice)) {
                manageNotes();
            } else if ("0".equals(choice)) {
                break;
            } else {
                System.out.println("А теперь посмотри внимательнее");
            }
        }
        scanner.close();
    }

    private void showSchedule() {
    System.out.println("\n~ Чётная неделя ~");
    printScheduleAsTable(scheduleService.getScheduleForWeek("EVEN"));

    System.out.println("\n~ Нечётная неделя ~");
    printScheduleAsTable(scheduleService.getScheduleForWeek("ODD"));
    }

        private void printScheduleAsTable(ArrayList<Lesson> lessons) {
    String[] days = {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"};
    
    // Сбор уникальных временных слотов с нормализацией
    java.util.Set<String> timeSlotSet = new java.util.HashSet<>();
    for (Lesson l : lessons) {
        String start = normalizeTimeForSorting(l.getStartTime());
        String end = normalizeTimeForSorting(l.getEndTime());
        timeSlotSet.add(start + "–" + end);
    }

    if (timeSlotSet.isEmpty()) {
        System.out.println("  Нет занятий.");
        return;
    }

    // Сортируем как время
    java.util.List<String> sortedTimes = new java.util.ArrayList<>(timeSlotSet);
    sortedTimes.sort((a, b) -> {
        String[] a1 = a.split("–")[0].split(":");
        String[] b1 = b.split("–")[0].split(":");
        int ha = Integer.parseInt(a1[0]), ma = Integer.parseInt(a1[1]);
        int hb = Integer.parseInt(b1[0]), mb = Integer.parseInt(b1[1]);
        if (ha != hb) return Integer.compare(ha, hb);
        return Integer.compare(ma, mb);
    });

    // Таблица: время -> день -> {предмет+аудитория, заметка}
    java.util.Map<String, java.util.Map<String, String[]>> table = new java.util.HashMap<>();
    for (String time : sortedTimes) {
        table.put(time, new java.util.HashMap<>());
        for (String day : days) {
            table.get(time).put(day, new String[]{"", ""});
        }
    }

    // Заполняем
    for (Lesson l : lessons) {
        String start = normalizeTimeForSorting(l.getStartTime());
        String end = normalizeTimeForSorting(l.getEndTime());
        String timeKey = start + "–" + end;
        String day = l.getDayOfWeek();

        String subject = l.getSubject() != null ? l.getSubject() : "";
        if (l.getRoom() != null && !l.getRoom().trim().isEmpty()) {
            subject += " (" + l.getRoom() + ")";
        }

        String note = (l.getNoteText() != null) ? l.getNoteText().trim() : "";
        table.get(timeKey).put(day, new String[]{subject, note});
    }

    int timeColWidth = 14;
    int dayColWidth = 20;

    // Заголовок
    System.out.printf("%-" + timeColWidth + "s", "Время");
    for (String day : days) {
        System.out.printf("%-" + dayColWidth + "s", day);
    }
    System.out.println();

    // Вывод
    for (String time : sortedTimes) {
        // Строка 1: предмет + аудитория
        System.out.printf("%-" + timeColWidth + "s", time);
        for (String day : days) {
            String s = table.get(time).get(day)[0];
            if (s.length() > dayColWidth - 1) s = s.substring(0, dayColWidth - 2) + "…";
            System.out.printf("%-" + dayColWidth + "s", s);
        }
        System.out.println();

        // Строка 2: заметка — ТОЛЬКО если она есть
        boolean hasNote = false;
        String[] notes = new String[days.length];
        for (int i = 0; i < days.length; i++) {
            notes[i] = table.get(time).get(days[i])[1];
            if (!notes[i].isEmpty()) hasNote = true;
        }

        if (hasNote) {
            System.out.printf("%-" + timeColWidth + "s", ""); 
            for (String n : notes) {
                if (n.length() > dayColWidth - 1) n = n.substring(0, dayColWidth - 2) + "…";
                System.out.printf("%-" + dayColWidth + "s", n);
            }
            System.out.println();
        }
    }
}

// Вспомогательный метод 
private String normalizeTimeForSorting(String time) {
    if (time == null) return "00:00";
    time = time.trim();
    if (time.matches("\\d:\\d\\d")) {
        return "0" + time;
    }
    return time;
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
            int num = Integer.parseInt(scanner.nextLine());
            if (num >= 1 && num <= lessons.size()) {
                Lesson selected = lessons.get(num - 1);
                showNotesForLesson(selected);
            } else {
                System.out.println(" Неверный номер.");
            }
        } catch (NumberFormatException e) {
            System.out.println(" Введите число.");
        }
    }

    private void showNotesForLesson(Lesson lesson) {
        while (true) {
            System.out.println("\nЗаметки к: " + lesson.getSubject());
            ArrayList<Note> notes = noteService.getNotesForLesson(lesson.getId());
            if (notes.isEmpty()) {
                System.out.println("  Нет заметок.");
            } else {
                for (int i = 0; i < notes.size(); i++) {
                    System.out.println("  " + (i + 1) + ". " + notes.get(i).getText());
                }
            }

            System.out.println("1. Добавить заметку");
            System.out.println("0. Назад");
            System.out.print("Выбор: ");
            String choice = scanner.nextLine();

            if ("1".equals(choice)) {
                System.out.print("Текст заметки: ");
                String text = scanner.nextLine();
                noteService.addNote(lesson.getId(), text);
                System.out.println(" Заметка добавлена.");
            } else if ("0".equals(choice)) {
                break;
            }
        }
    }
}