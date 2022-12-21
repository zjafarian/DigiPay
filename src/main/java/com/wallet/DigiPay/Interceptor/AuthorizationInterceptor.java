package com.wallet.DigiPay.Interceptor;

import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.authException.NoBearerTokenError;
import com.wallet.DigiPay.services.impls.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Autowired
    private AuthService authService;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("in AuthorizationInterceptor preHandle....");

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NoBearerTokenError();
        }


        User user = authService.getUserFromAccessToken(authorizationHeader.substring(7));
        System.out.println("user nationalCode : " + user.getNationalCode());

        request.setAttribute("user", user);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
