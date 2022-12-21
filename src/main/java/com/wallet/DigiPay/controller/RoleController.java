package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.RoleRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;

@RestController
@RequestMapping("/roles")
@Validated
public class RoleController {


    @Autowired
    RoleServiceImpl roleService;


    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addRole(@Valid @RequestBody RoleRequestDto roleRequestDto)
    throws NullPointerException{

        Role role = roleService.generateRole(roleRequestDto);
        roleService.save(role);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(role,
                        "Role created successful",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getRole(@PathVariable Long id)
            throws NotFoundException {


        RoleRequestDto roleRequestDto = roleService.generateRoleDto(roleService.findById(id).get());


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(roleRequestDto,
                        "Role created successful",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }









    


}
