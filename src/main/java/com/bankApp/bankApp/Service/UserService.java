package com.bankApp.bankApp.Service;

import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (user.getEmail() != null && user.getPassword() != null && isValidEmail(user.getEmail()) && isValidPassword(user.getPassword())) {
            return userRepository.save(user);
        }
        return null;
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    private boolean isValidPassword(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    }

}
