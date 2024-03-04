package com.bankApp.bankApp.Service;

import com.bankApp.bankApp.CustomException.InvalidTransactionAmountException;
import com.bankApp.bankApp.Model.Account;
import com.bankApp.bankApp.Repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckBalance() {
        int userId = 123;
        double balance = 100.0;
        Account account = new Account();
        account.setBalance(balance);
        when(accountRepository.findByUser_UserId(userId)).thenReturn(Optional.of(account));

        double actualBalance = accountService.checkBalance(userId);

        assertEquals(balance, actualBalance);
    }

    @Test
    public void testDeposit() {
        int userId = 123;
        double amount = 50.0;
        Account account = new Account();
        account.setBalance(100.0);
        when(accountRepository.findByUser_UserId(userId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.deposit(userId, amount);

        assertEquals(150.0, updatedAccount.getBalance());
    }

    @Test
    public void testWithdraw() {
        int userId = 123;
        double amount = 50.0;
        Account account = new Account();
        account.setBalance(100.0);
        when(accountRepository.findByUser_UserId(userId)).thenReturn(Optional.of(account));
        when(accountRepository.save(account)).thenReturn(account);

        Account updatedAccount = accountService.withdraw(userId, amount);

        assertEquals(50.0, updatedAccount.getBalance());
    }

    @Test
    public void testWithdrawInsufficientFunds() {
        int userId = 123;
        double amount = 150.0;
        Account account = new Account();
        account.setBalance(100.0);
        when(accountRepository.findByUser_UserId(userId)).thenReturn(Optional.of(account));

        assertThrows(InvalidTransactionAmountException.class, () -> accountService.withdraw(userId, amount));
    }

    @Test
    public void testTransfer() {
        int fromUserId = 123;
        int toUserId = 456;
        double amount = 50.0;
        Account fromAccount = new Account();
        fromAccount.setBalance(100.0);
        Account toAccount = new Account();
        toAccount.setBalance(100.0);
        when(accountRepository.findByUser_UserId(fromUserId)).thenReturn(Optional.of(fromAccount));
        when(accountRepository.findByUser_UserId(toUserId)).thenReturn(Optional.of(toAccount));
        when(accountRepository.save(fromAccount)).thenReturn(fromAccount);
        when(accountRepository.save(toAccount)).thenReturn(toAccount);

        accountService.transfer(fromUserId, toUserId, amount);

        assertEquals(50.0, fromAccount.getBalance());
        assertEquals(150.0, toAccount.getBalance());
    }
}
