package com.wallet.DigiPay.exceptions;

public class WalletActiveException extends RuntimeException {
    public WalletActiveException(String message) {
        super(message);
    }
}
