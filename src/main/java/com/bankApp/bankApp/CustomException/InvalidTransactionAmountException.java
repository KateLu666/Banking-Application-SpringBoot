package com.bankApp.bankApp.CustomException;

public class InvalidTransactionAmountException extends RuntimeException {
    public InvalidTransactionAmountException(String message) {
        super(message);
    }
}
