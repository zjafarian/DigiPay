package com.wallet.DigiPay.security;

import lombok.Getter;

public class LoginTokens {
    @Getter
    private final Token accessToken;
    @Getter
    private final Token refreshToken;



    public LoginTokens(Token accessToken, Token refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static LoginTokens of(String nationalCode, String accessSecretKey, Long accessSecretKeyValidityInMinutes, String refreshSecretKey, Long refreshSecretKeyValidityInMinutes) {
        //Token.of(email, 10L , accessSecretKey);
        //Token.of(email, 1440L , refreshSecretKey);

        return new LoginTokens(Token.of(nationalCode, Long.valueOf(accessSecretKeyValidityInMinutes), accessSecretKey),
                Token.of(nationalCode, Long.valueOf(refreshSecretKeyValidityInMinutes), refreshSecretKey));
    }

    public static LoginTokens of(String nationalCode, String accessSecretKey, Long accessSecretKeyValidityInMinutes, String refreshToken) {
        //Token.of(email, 10L , accessSecretKey);
        //Token.of(email, 1440L , refreshSecretKey);

        return new LoginTokens(Token.of(nationalCode, Long.valueOf(accessSecretKeyValidityInMinutes), accessSecretKey),
                Token.of( refreshToken));
    }
}
