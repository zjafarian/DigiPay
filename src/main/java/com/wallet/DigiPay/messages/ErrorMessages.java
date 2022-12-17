package com.wallet.DigiPay.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ErrorMessages {

    @Value("${national.code.not.valid}")
    private String MESSAGE_NOT_VALID_NATIONAL_CODE;


    @Value("${phone.number.not.unique}")
    private String MESSAGE_PHONE_IS_EXISTED;

    @Value("${entry.null}")
    private String MESSAGE_NULL_ENTRY;



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
}