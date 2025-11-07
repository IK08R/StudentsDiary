import service.AuthService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Ежедневник студента!");
        AuthService auth = new AuthService();
        auth.start();
        // запустить главное меню после успешной авторизации
    }
}
