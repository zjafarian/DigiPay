package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.RoleRequestDto;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@RequestMapping("/roles")
public class RoleController {


    @Autowired
    RoleServiceImpl roleService;







    


}
