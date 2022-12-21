package com.wallet.DigiPay.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;

import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

public class Token {
    @Getter
    private String token;

    private Token(String token) {
        this.token = token;
    }

    public static Token of(String nationalCode, Long validityInMinutes, String secretKey){

        var issueDate = Instant.now();
        var generated  = Jwts.builder().claim("nationalCode" , nationalCode )
                .setIssuedAt(Date.from(issueDate))
                .setExpiration(Date.from(issueDate.plus(validityInMinutes , ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .compact();

        return new Token(generated) ;
    }

    public static Token of(String token){
        return new Token(token);
    }

    public static String from(String token, String secretKey) throws ExpiredJwtException {

        Claims claims= (Claims) Jwts.parserBuilder().setSigningKey( Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parse(token)
                .getBody();

        return claims.get("nationalCode", String.class);


    }
}
