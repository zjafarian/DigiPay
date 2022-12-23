package com.wallet.DigiPay.controller;


import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistNationalCodeException;
import com.wallet.DigiPay.exceptions.NationalCodeException;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.exceptions.PasswordException;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.security.jwt.JwtUtils;
import com.wallet.DigiPay.security.models.LoginRequest;
import com.wallet.DigiPay.security.models.LoginResponse;
import com.wallet.DigiPay.security.service.AuthService;
import com.wallet.DigiPay.security.service.UserDetailsImpl;
import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import com.wallet.DigiPay.utils.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;


    @Autowired
    AuthService authService;




    @GetMapping("/testOrigins")
    public String testOrigin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("uri: " + request.getRequestURI());
        System.out.println("url: " + request.getRequestURL());

        return "tested Origins";
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<?>> register(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ConstraintViolationException,
            NullPointerException,
            ExistNationalCodeException,
            NationalCodeException {


        User user = authService.registerUser(userRequestDto);


        //create response
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(user,
                        "user Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }


    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getNationalCode(), loginRequest.getPassword()));


        LoginResponse loginResponse = authService.login(authentication,loginRequest);





        //create LoginResponse and return it
        return loginResponse;
    }


    //get user with token
    @GetMapping("/user")
    public ResponseEntity<ResponseMessage<?>> findUserFromToken(HttpServletRequest request)
            throws NotFoundException {


        //create userRequestDto with User
        UserRequestDto userRequestDto = authService.getUser(request);


        //create response
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userRequestDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


}

