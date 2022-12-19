package com.wallet.DigiPay.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ErrorMessages {

    @Value("${national.code.not.valid}")
    private String MESSAGE_NOT_VALID_NATIONAL_CODE;

    @Value("${phone.number.not.valid}")
    private String MESSAGE_PHONE_IS_NOT_CORRECT;


    @Value("${phone.number.not.unique}")
    private String MESSAGE_PHONE_IS_EXISTED;

    @Value("${entry.null}")
    private String MESSAGE_NULL_ENTRY;

    @Value("${password.not.valid }")
    private String MESSAGE_PASSWORD_NOT_VALID;

    @Value("${not.found.user}")
    private String MESSAGE_NOT_FOUND_USER;

    @Value("${not.found.wallet}")
    private String MESSAGE_NOT_FOUND_WALLET;

    @Value("${amount.zero.error}")
    private String MESSAGE_ZERO_AMOUNT;

    @Value("${de.active.wallet.error}")
    private String MESSAGE_DE_ACTIVE_WALLET;



    public ErrorMessages() {
    }

    public String getMESSAGE_NOT_VALID_NATIONAL_CODE() {
        return MESSAGE_NOT_VALID_NATIONAL_CODE;
    }

    public String getMESSAGE_PHONE_IS_EXISTED() {
        return MESSAGE_PHONE_IS_EXISTED;
    }

    public String getMESSAGE_NULL_ENTRY() {
        return MESSAGE_NULL_ENTRY;
    }

    public String getMESSAGE_PHONE_IS_NOT_CORRECT() {
        return MESSAGE_PHONE_IS_NOT_CORRECT;
    }

    public String getMESSAGE_PASSWORD_NOT_VALID() {
        return MESSAGE_PASSWORD_NOT_VALID;
    }

    public String getMESSAGE_NOT_FOUND_USER() {
        return MESSAGE_NOT_FOUND_USER;
    }

    public String getMESSAGE_ZERO_AMOUNT() {
        return MESSAGE_ZERO_AMOUNT;
    }

    public String getMESSAGE_NOT_FOUND_WALLET() {
        return MESSAGE_NOT_FOUND_WALLET;
    }

    public String getMESSAGE_DE_ACTIVE_WALLET() {
        return MESSAGE_DE_ACTIVE_WALLET;
    }
}
