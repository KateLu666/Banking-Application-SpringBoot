package com.bankApp.bankApp.Service;

import com.bankApp.bankApp.CustomException.InvalidEmailException;
import com.bankApp.bankApp.CustomException.InvalidPasswordException;
import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Repository.UserRepository;
import com.bankApp.bankApp.Utili.DTO.LoginCreds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (!isValidEmail(user.getEmail())) {
            throw new InvalidEmailException("Invalid email format");
        }

        if (!isValidPassword(user.getPassword())) {
            throw new InvalidPasswordException("Invalid password format, at least 8 characters long and contain at least one letter and one number");
        }

        Optional<User> existingUser = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if (existingUser.isPresent()) {
            throw new InvalidEmailException("Email already exists");
        }

        return userRepository.save(user);
    }

    public User loginUser(LoginCreds loginCreds) {
        User user = userRepository.findByEmail(loginCreds.getEmail());
        if (user == null) {
            throw new InvalidEmailException("Email does not exist");
        }

        if (!user.getPassword().equals(loginCreds.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        return user;
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pattern.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordRegex);
        return password != null && pattern.matcher(password).matches();
    }


}
