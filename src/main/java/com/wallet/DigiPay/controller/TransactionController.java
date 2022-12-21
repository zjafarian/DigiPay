package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.TransactionServiceImpl;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wallets")
public class TransactionController {


    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/{id}/transactions")
    public ResponseEntity<ResponseMessage<?>> getTransactions(@PathVariable Long id){

        transactionService.getTransactions(id);

        return null;
    }


    


}
