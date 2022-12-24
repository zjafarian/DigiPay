package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.*;
import com.wallet.DigiPay.exceptions.*;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.TransactionServiceImpl;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import com.wallet.DigiPay.services.impls.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/wallets")
@Validated
public class WalletController {


    @Autowired
    WalletServiceImpl walletService;

    @Autowired
    TransactionServiceImpl transactionService;

    @Autowired
    UserServiceImpl userService;


    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addWallet(@Valid @RequestBody WalletDto walletDto)
            throws NullPointerException, AmountException {


        Wallet wallet = walletService.generateWallet(walletDto);
        wallet.setWalletNumber(UUID.randomUUID().toString());
        wallet = walletService.save(wallet);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(wallet,
                        "wallet Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getWallet(@PathVariable Long id) throws NotFoundException {

        WalletDto walletDto = walletService.generateWalletDto(walletService.findById(id).get());


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "wallet find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);

    }


    @PutMapping
    public ResponseEntity<ResponseMessage<?>> updateWallet(@Valid @RequestBody WalletDto walletDto) {

        walletDto = walletService.generateWalletDto(walletService.update(walletService.generateWallet(walletDto)));


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "wallet Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }


    @PutMapping("/deposit")
    @Transactional
    public ResponseEntity<ResponseMessage<?>> depositWallet
            (@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws NotFoundException,
            WalletActiveException,
            AmountException,
            CartNumberException,
            TransactionException {


        //do transaction
        Transaction transaction = transactionService.save(walletService.deposit(transactionRequestDto));

        //if result was success from result of IGP, status of transaction will change to success and update
        transaction.setTransactionStatus(TransactionStatus.Success);

        //update transaction
        transactionService.update(transaction);


        WalletDto walletDto = walletService.generateWalletDto(walletService.update(walletService.returnWallet(transaction)));


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "The wallet has been successfully upgraded",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);

    }


    @PutMapping("/withdraw")
    @Transactional
    public ResponseEntity<ResponseMessage<?>> withdrawWallet
            (@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws NotFoundException,
            NullPointerException,
            WalletActiveException,
            AmountException,
            CartNumberException,
            TransactionException {


        //do transaction
        Transaction transaction = transactionService.save(walletService.withdraw(transactionRequestDto));

        //if result was success from result of IGP, status of transaction will change to success and update
        transaction.setTransactionStatus(TransactionStatus.Success);

        //update transaction
        transactionService.update(transaction);


        WalletDto walletDto = walletService.generateWalletDto(walletService.update(walletService.returnWallet(transaction)));


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "The withdrawal from the wallet was successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);

    }


    @PutMapping("/transferWalletToWallet")
    public ResponseEntity<ResponseMessage<?>> transferFromWalletToWallet
            (@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws NotFoundException,
            NullPointerException,
            WalletActiveException,
            AmountException,
            TransactionException,
            WalletNumberException {

        //create transactions
        List<Transaction> transactions = walletService.walletToWallet(transactionRequestDto);

        //do transactions
        transactions.stream().forEach(transaction -> transactionService.save(transaction));


        transactions.stream().forEach(transaction -> {

            //if result was success from result of IGP, status of transaction will change to success and update
            transaction.setTransactionStatus(TransactionStatus.Success);

            //update transaction
            transactionService.update(transaction);
        });


        //update wallets
        transactions.stream().forEach(transaction -> {
            walletService.update(walletService.findById(transaction.getWallet().getId()).get());
        });


        WalletDto walletDto = walletService.generateWalletDto(walletService.returnWallet(transactions.get(0)));


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "Transfer wallet to wallet was successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }


    @PutMapping("/changeWalletStatus")
    public ResponseEntity<ResponseMessage<?>> changeWalletActivity(@RequestBody WalletDto walletDto) {


        Wallet wallet = walletService.update(walletService.changeActiveWallet(walletDto));

        walletDto = walletService.generateWalletDto(wallet);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "Transfer wallet to wallet was successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);


    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> deleteWallet(@PathVariable Long id)
            throws NotFoundException {

        walletService.delete(id);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(null,
                        "delete wallet successful",
                        "message");
        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


}
