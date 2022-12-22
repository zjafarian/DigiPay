package com.wallet.DigiPay.controller;


import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistNationalCodeException;
import com.wallet.DigiPay.exceptions.NationalCodeException;
import com.wallet.DigiPay.exceptions.PasswordException;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.security.jwt.JwtUtils;
import com.wallet.DigiPay.security.models.LoginRequest;
import com.wallet.DigiPay.security.models.LoginResponse;
import com.wallet.DigiPay.security.service.UserDetailsImpl;
import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import com.wallet.DigiPay.utils.PasswordValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ErrorMessages errorMessages;


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


        //check password is valid or not
        if (!PasswordValidation.validationPassword(userRequestDto.getPassword()))
            throw new PasswordException(errorMessages.getMESSAGE_PASSWORD_NOT_VALID());


        userRequestDto.setPassword(encoder.encode(userRequestDto.getPassword()));
        Role role = roleService.findById(userRequestDto.getRoleId()).get();
        User user = userService.generateUser(userRequestDto);
        user.setRole(role);

        userService.save(user);


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

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0));


        //create LoginResponse and return it
        return loginResponse;
    }


    //get user with token
    @GetMapping("/user")
    public ResponseEntity<ResponseMessage<?>> findUserFromToken(HttpServletRequest request) {


        String nationalCode = "";
        String token = request.getHeader("Authorization");
        token = token.split(" ")[1].trim();






        //token = token.substring("Bearer ".length());
        //if (jwtUtils.validateJwtToken(token))
        nationalCode = jwtUtils.getUserNameFromJwtToken(token);


        //get user with request
        User user = (User) request.getAttribute(RoleType.User.toString());

        System.out.println(RoleType.User.toString() + ": " + user);


        //create userRequestDto with User
        UserRequestDto userRequestDto = userService.generateUserRequestDto(user);


        //create response
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userRequestDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


}

