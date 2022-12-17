package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistPhoneNumberException;
import com.wallet.DigiPay.mapper.UserMapper;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserMapper userMapper;

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ConstraintViolationException, NullPointerException, ExistPhoneNumberException {

        User user = userMapper.toUser(userRequestDto);
        userService.save(user);

        ResponseMessage responseMessage = ResponseMessage.withResponseData(user, "user Created Successfully", "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }


}
