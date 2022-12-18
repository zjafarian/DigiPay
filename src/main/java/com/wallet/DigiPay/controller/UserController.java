package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.RoleDto;
import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistPhoneNumberException;
import com.wallet.DigiPay.exceptions.NationalCodeException;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.mapper.RoleMapper;
import com.wallet.DigiPay.mapper.UserMapper;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {


    @Autowired
    UserServiceImpl userService;


    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ConstraintViolationException,
            NullPointerException,
            ExistPhoneNumberException,
            NationalCodeException {

        User user = userService.generateUser(userRequestDto);
        userService.save(user);

        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(user,
                        "user Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getUser(@PathVariable Long id)
            throws NotFoundException, NullPointerException {

        Optional<User> user = userService.findById(id);

        UserDto userDto = userService.generateUserDto(user.get());

        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


    @PutMapping
    public ResponseEntity<ResponseMessage<?>> updateUser(@RequestBody UserRequestDto userRequestDto)
            throws NotFoundException, NullPointerException {

        UserDto userDto = userService.generateUserDto(userService.generateUser(userRequestDto));

        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userDto,
                        "update user successful",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }

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
