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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

        User user = walletService.getUser(walletDto.getUserId());


        Wallet wallet = walletService.generateWallet(walletDto);
        wallet.setWalletNumber(UUID.randomUUID().toString());
        wallet.setActive(true);
        wallet.setUser(user);


        walletService.save(wallet);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(wallet,
                        "wallet Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseMessage<?>> getWallet(@PathVariable Long id) throws NotFoundException {

        Optional<Wallet> wallet = walletService.findById(id);
        WalletDto walletDto = walletService.generateWalletDto(wallet.get());


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "wallet find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);

    }


    @PutMapping
    public ResponseEntity<ResponseMessage<?>> updateWallet(@Valid @RequestBody WalletDto walletDto) {

        Wallet wallet = walletService.update(walletService.generateWallet(walletDto));
        walletDto = walletService.generateWalletDto(wallet);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "wallet Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }


    @PutMapping("/deposit")
    public ResponseEntity<ResponseMessage<?>> depositWallet
            (@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws NotFoundException,
            WalletActiveException,
            AmountException,
            CartNumberException,
            TransactionException {



        Wallet wallet = walletService
                .depositWallet(transactionRequestDto.getAmount(),
                        transactionRequestDto.getWalletId());




        Transaction transaction = transactionService.generateTransaction(transactionRequestDto);


        transaction.setDestination(wallet.getWalletNumber());
        transaction.setUser(userService.findById(wallet.getUser().getId()).get());
        transaction.setWallet(wallet);
        transaction.setWalletBalance(wallet.getBalance());



        transaction.setTransactionStatus(TransactionStatus.Success);

        transaction = transactionService.save(transaction);


        transactionService.update(transaction);
        walletService.update(wallet);

        WalletDto walletDto = walletService.generateWalletDto(wallet);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(walletDto,
                        "The wallet has been successfully upgraded",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);

    }


    @PutMapping("/withdraw")
    public ResponseEntity<ResponseMessage<?>> withdrawWallet
            (@Valid @RequestBody TransactionRequestDto transactionRequestDto)
            throws NotFoundException,
            NullPointerException,
            WalletActiveException,
            AmountException,
            CartNumberException,
            TransactionException {


        Wallet wallet = walletService
                .withdrawWallet(transactionRequestDto.getAmount(),
                        transactionRequestDto.getWalletId());

        transactionRequestDto.setSource(wallet.getWalletNumber());



        Transaction transaction = transactionService.generateTransaction(transactionRequestDto);
        transaction.setUser(userService.findById(wallet.getUser().getId()).get());
        transaction.setWallet(wallet);
        transaction.setWalletBalance(wallet.getBalance());
        transaction.setDestination(transactionRequestDto.getDestination());







        transaction = transactionService.save(transaction);

        transaction.setTransactionStatus(TransactionStatus.Success);

        transactionService.update(transaction);
        walletService.update(wallet);

        WalletDto walletDto = walletService.generateWalletDto(wallet);


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
            TransactionException {

        List<Wallet> wallets = walletService.transferFromWalletToWallet(transactionRequestDto.getAmount(),
                transactionRequestDto.getWalletId(),
                transactionRequestDto.getDestination());


        transactionRequestDto.setTransactionType(TransactionType.TransferWTW_Withdraw);
        transactionRequestDto.setDestination(wallets.get(1).getWalletNumber());


        Transaction transaction1 = transactionService.generateTransaction(transactionRequestDto);
        transaction1.setUser(userService.findById(wallets.get(0).getUser().getId()).get());
        transaction1.setWallet(wallets.get(0));
        transaction1.setSource(wallets.get(0).getWalletNumber());
        transaction1.setDestination(wallets.get(1).getWalletNumber());


        transactionRequestDto.setTransactionType(TransactionType.TransferWTW_Deposit);
        transactionRequestDto.setSource(wallets.get(0).getWalletNumber());



        Transaction transaction2 = transactionService.generateTransaction(transactionRequestDto);
        transaction2.setUser(userService.findById(wallets.get(1).getUser().getId()).get());
        transaction2.setWallet(wallets.get(1));
        transaction2.setSource(wallets.get(0).getWalletNumber());
        transaction1.setDestination(wallets.get(1).getWalletNumber());

        transaction1 = transactionService.save(transaction1);
        transaction2 = transactionService.save(transaction2);

        transaction1.setTransactionStatus(TransactionStatus.Success);
        transaction2.setTransactionStatus(TransactionStatus.Success);

        transactionService.update(transaction1);
        transactionService.update(transaction2);

        walletService.update(wallets.get(0));
        walletService.update(wallets.get(1));

        WalletDto walletDto = walletService.generateWalletDto(wallets.get(0));


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
