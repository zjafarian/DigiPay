package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.services.impls.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {


    @Autowired
    RoleServiceImpl roleService;




    


}
