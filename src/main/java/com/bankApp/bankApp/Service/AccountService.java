package com.bankApp.bankApp.Service;


import com.bankApp.bankApp.CustomException.InvalidTransactionAmountException;
import com.bankApp.bankApp.Model.Account;
import com.bankApp.bankApp.Repository.AccountRepository;
import jakarta.transaction.Transactional;
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

    public Account deposit(int userId, double amount) {
        Account account = accountRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Account not found for User ID: " + userId));

        if (amount <= 0) {
            throw new InvalidTransactionAmountException("Amount to deposit must be positive.");
        }

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(int userId, double amount) {
        Account account = accountRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Account not found for User ID: " + userId));

        if (amount <= 0) {
            throw new InvalidTransactionAmountException("Amount to withdraw must be positive.");
        }

        if (account.getBalance() < amount) {
            throw new InvalidTransactionAmountException("Insufficient funds to withdraw. Current balance: " + account.getBalance() + ". Amount to withdraw: " + amount);
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    @Transactional
    public void transfer(int fromUserId, int toUserId, double amount) {
       Account fromAccount = accountRepository.findByUser_UserId(fromUserId)
                .orElseThrow(() -> new NoSuchElementException("Account not found for User ID: " + fromUserId));
        Account toAccount = accountRepository.findByUser_UserId(toUserId)
                .orElseThrow(() -> new NoSuchElementException("Account not found for User ID: " + toUserId));

        if (amount <= 0) {
            throw new InvalidTransactionAmountException("Amount to transfer must be positive.");
        }

        if (fromAccount.getBalance() < amount) {
            throw new InvalidTransactionAmountException("Insufficient funds to transfer. Current balance: " + fromAccount.getBalance() + ". Amount to transfer: " + amount);
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }
}
