package com.wallet.DigiPay.controller;


import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {


    @Autowired
    UserServiceImpl userService;


    //get user with id. this method use from admin

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getUser(@PathVariable Long id)
            throws NotFoundException, NullPointerException {

        Optional<User> user = userService.findById(id);

        UserRequestDto  userRequestDto= userService.generateUserRequestDto(user.get());



        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userRequestDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


    //update user
    @PutMapping
    public ResponseEntity<ResponseMessage<?>> updateUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws NotFoundException, NullPointerException {

        User user = userService.update(userService.generateUser(userRequestDto));



        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(user,
                        "update user successful",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }

    //delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> deleteUser(@PathVariable Long id)
            throws NotFoundException {

        userService.delete(id);

        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(null,
                        "delete user successful",
                        "message");
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


}
