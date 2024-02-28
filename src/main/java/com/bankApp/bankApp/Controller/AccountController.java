package com.bankApp.bankApp.Controller;

import com.bankApp.bankApp.CustomException.InvalidTransactionAmountException;
import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Model.Account;
import com.bankApp.bankApp.Service.AccountService;
import com.bankApp.bankApp.Utili.DTO.TransactionRequest;
import com.bankApp.bankApp.Utili.DTO.TransferRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return ResponseEntity.badRequest().body("No user logged in. Please login to check balance.");
        }
        double balance = accountService.checkBalance(user.getUserId());
        return ResponseEntity.ok(balance);
    }

    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestBody TransactionRequest transactionRequest, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("No user logged in. Please login to deposit.");
        }

        Account account = accountService.deposit(user.getUserId(), transactionRequest.getAmount());
        return ResponseEntity.ok("Amount deposited successfully. New balance: " + account.getBalance());
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestBody TransactionRequest transactionRequest, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("No user logged in. Please login to withdraw.");
        }

        Account account = accountService.withdraw(user.getUserId(), transactionRequest.getAmount());
        return ResponseEntity.ok("Amount withdrawn successfully. New balance: " + account.getBalance());
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody TransferRequest transferRequest, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.badRequest().body("No user logged in. Please login to transfer.");
        }

        if (user.getUserId() == transferRequest.getToUserId()) {
            return ResponseEntity.badRequest().body("Cannot transfer to same account.");
        }

        if (user.getUserId() != transferRequest.getFromUserId()) {
            return ResponseEntity.badRequest().body("Logged in user ID does not match with fromUserId.");
        }

        accountService.transfer(transferRequest.getFromUserId(), transferRequest.getToUserId(), transferRequest.getAmount());
        return ResponseEntity.ok("Amount transferred successfully. New balance: " + accountService.checkBalance(user.getUserId()));
    }

    @ExceptionHandler(InvalidTransactionAmountException.class)
    public ResponseEntity<?> handleInvalidTransactionAmountException(InvalidTransactionAmountException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
