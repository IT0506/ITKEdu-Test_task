package com.example.wallet.exception;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException() {
        super("Invalid amount");
    }
}