package com.wallet.DigiPay.security.service.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class JWTIsExpired extends ResponseStatusException {

    public JWTIsExpired(){
        super(HttpStatus.BAD_REQUEST, "jwt is expired...");

    }

    public JWTIsExpired(String message){
        super(HttpStatus.BAD_REQUEST, message);

    }

}