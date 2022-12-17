package com.wallet.DigiPay.messages;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ErrorMessages {

    @Value("${national.code.not.valid}")
    private String MESSAGE_NOT_VALID_NATIONAL_CODE;


    public ErrorMessages() {
    }

    public String getMESSAGE_NOT_VALID_NATIONAL_CODE() {
        return MESSAGE_NOT_VALID_NATIONAL_CODE;
    }
}
