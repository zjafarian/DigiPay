package com.wallet.DigiPay.dto;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.Size;

@Validated
public class UserRequestDto {


    @Size(min = 10, max = 10)
    private String nationalCode;

    @Size(min = 11, max = 11)
    private String phoneNumber;

    public UserRequestDto() {
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
