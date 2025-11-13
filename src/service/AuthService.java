package service;

import model.User;
import data.UserRepository;

public class AuthService {
    private final UserRepository userRepository = new UserRepository();
    private User currentUser = null;

    public User login(String login, String password) {
        if (userRepository.validatePassword(login, password)) {
            currentUser = userRepository.findByLogin(login);
            return currentUser;
        }
        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void logout() {
        currentUser = null;
    }
}
