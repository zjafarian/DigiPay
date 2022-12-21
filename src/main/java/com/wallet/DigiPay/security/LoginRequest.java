package com.wallet.DigiPay.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class LoginRequest {

    private String nationalCode;
    private String password;

}
