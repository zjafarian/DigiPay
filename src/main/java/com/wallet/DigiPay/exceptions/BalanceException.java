package com.wallet.DigiPay.exceptions;

public class BalanceException extends RuntimeException {
    public BalanceException(String message) {
        super(message);
    }
}
