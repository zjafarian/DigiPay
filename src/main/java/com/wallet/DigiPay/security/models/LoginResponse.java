package com.wallet.DigiPay.security.models;

import com.wallet.DigiPay.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String nationalCode;
    private String role;

    public LoginResponse(String accessToken, Long id, String nationalCode, String role) {
        this.token = accessToken;
        this.id = id;
        this.nationalCode = nationalCode;
        this.role = role;
    }


}
