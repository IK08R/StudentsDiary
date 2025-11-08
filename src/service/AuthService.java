package service;

import model.User;
import java.util.Scanner;

public class AuthService {
    public void start() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String login = sc.nextLine();
        System.out.print("Введите пароль: ");
        String password = sc.nextLine();
        System.out.println("Авторизация успешна (заглушка).");
        sc.close();
    }
}
