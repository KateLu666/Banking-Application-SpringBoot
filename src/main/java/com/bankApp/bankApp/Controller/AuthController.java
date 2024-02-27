package com.bankApp.bankApp.Controller;

import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "*", maxAge = 360000)
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
       User registeredUser = userService.registerUser(user);
       return ResponseEntity.ok(registeredUser);
    }


}
