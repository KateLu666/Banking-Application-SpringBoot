package com.bankApp.bankApp.Controller;

import com.bankApp.bankApp.CustomException.InvalidTransactionAmountException;
import com.bankApp.bankApp.Model.Account;
import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Service.AccountService;
import com.bankApp.bankApp.Utili.DTO.TransactionRequest;
import com.bankApp.bankApp.Utili.DTO.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private User user;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AccountController accountController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCheckBalance() {
        double expectedBalance = 100;
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getUserId()).thenReturn(123);
        when(accountService.checkBalance(123)).thenReturn(expectedBalance);

        ResponseEntity<?> responseEntity = accountController.checkBalance(session);
        double actualBalance = (double) responseEntity.getBody();

        assertEquals(expectedBalance, actualBalance);
    }

    @Test
    public void testDeposit() {
        double depositAmount = 500.0;
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getUserId()).thenReturn(123);
        when(accountService.deposit(123, depositAmount)).thenReturn(new Account());

        accountController.deposit(new TransactionRequest(depositAmount), session);

        verify(accountService, times(1)).deposit(123, depositAmount);
    }

    @Test
    public void testWithdraw() {
        double withdrawAmount = 200.0;
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getUserId()).thenReturn(123);
        when(accountService.withdraw(123, withdrawAmount)).thenReturn(new Account());

        accountController.withdraw(new TransactionRequest(withdrawAmount), session);

        verify(accountService, times(1)).withdraw(123, withdrawAmount);
    }

    @Test
    public void testTransfer() {
        TransferRequest transferRequest = new TransferRequest(123, 456, 100.0);
        when(session.getAttribute("user")).thenReturn(user);
        when(user.getUserId()).thenReturn(123);
        doNothing().when(accountService).transfer(123, 456, 100.0);

        accountController.transfer(transferRequest, session);

        verify(accountService, times(1)).transfer(123, 456, 100.0);
    }
}
