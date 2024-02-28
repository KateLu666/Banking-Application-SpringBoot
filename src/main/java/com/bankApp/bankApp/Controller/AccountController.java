package com.bankApp.bankApp.Controller;

import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping(path = "/account")
@CrossOrigin(origins = {"http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*", exposedHeaders = "*", maxAge = 360000)
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/balance")
    public ResponseEntity<?> checkBalance(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("No user logged in");
        }
        double balance = accountService.checkBalance(user.getUserId());
        return ResponseEntity.ok(balance);
    }

}
