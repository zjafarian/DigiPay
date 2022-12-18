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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {


    @Autowired
    UserServiceImpl userService;

    @Autowired
    RoleServiceImpl roleService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addUser(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ConstraintViolationException,
            NullPointerException,
            ExistPhoneNumberException,
            NationalCodeException {

        User user = userMapper.toUser(userRequestDto);
        userService.save(user);

        ResponseMessage responseMessage = ResponseMessage.withResponseData(user, "user Created Successfully", "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getUser(@PathVariable Long id) throws NotFoundException {

        Optional<User> user = userService.findById(id);

        UserDto userDto = userMapper.toUserDto(user.get(), roleMapper.toRoleDtos(roleService.findRoles(user.get())));


        ResponseMessage responseMessage = ResponseMessage.withResponseData(userDto, "user find", "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


}
