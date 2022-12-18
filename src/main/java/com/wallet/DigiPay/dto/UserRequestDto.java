package com.wallet.DigiPay.dto;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Validated
public class UserRequestDto {


    @Size(min = 10, max = 10,message = "size of phoneNumber isn't correct!")
    @NotBlank
    private String nationalCode;

    @Size(min = 11, max = 11,message = "size of national code isn't correct!")
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
