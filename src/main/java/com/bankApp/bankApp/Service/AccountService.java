package com.bankApp.bankApp.Service;


import com.bankApp.bankApp.Model.Account;
import com.bankApp.bankApp.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.NoSuchElementException;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public double checkBalance(int userId) {
        Account account = accountRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Account not found for User ID: " + userId));
        return account.getBalance();
    }
}