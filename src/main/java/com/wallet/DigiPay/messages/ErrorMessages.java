package com.wallet.DigiPay.messages;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
@Getter
public class ErrorMessages {

    @Value("${national.code.not.valid}")
    private String MESSAGE_NOT_VALID_NATIONAL_CODE;

    @Value("${phone.number.not.valid}")
    private String MESSAGE_PHONE_IS_NOT_CORRECT;


    @Value("${phone.number.not.unique}")
    private String MESSAGE_PHONE_IS_EXISTED;

    @Value("${entry.null}")
    private String MESSAGE_NULL_ENTRY;

    @Value("${password.not.valid}")
    private String MESSAGE_PASSWORD_NOT_VALID;

    @Value("${not.found.user}")
    private String MESSAGE_NOT_FOUND_USER;

    @Value("${not.found.wallet}")
    private String MESSAGE_NOT_FOUND_WALLET;

    @Value("${not.found.role}")
    private String MESSAGE_NOT_FOUND_ROLE;

    @Value("${amount.less.than.zero.error}")
    private String MESSAGE_LESS_THAN_ZERO_AMOUNT;

    @Value("${amount.zero.error}")
    private String MESSAGE_ZERO_AMOUNT;

    @Value("${de.active.wallet.error}")
    private String MESSAGE_DE_ACTIVE_WALLET;

    @Value("${wrong.cart.number}")
    private String MESSAGE_WRONG_CART_NUMBER;

    @Value("${transaction.failed.error}")
    private String MESSAGE_FAILED_TRANSACTION;

    @Value("${wallet.balance.error}")
    private String MESSAGE_WALLET_BALANCE;

    @Value("${equal.wallet.number.error}")
    private String MESSAGE_WALLETS_NUMBERS_COULD_NOT_EQUAL;



    public ErrorMessages() {
    }


}
