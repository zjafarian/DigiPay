package com.wallet.DigiPay.controller;

import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.exceptions.BalanceException;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import com.wallet.DigiPay.services.impls.WalletServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/wallets")
@Validated
public class WalletController {


    @Autowired
    WalletServiceImpl walletService;


    @PostMapping
    public ResponseEntity<ResponseMessage<?>> addWallet(@Valid @RequestBody WalletDto walletDto)
            throws NullPointerException, BalanceException {

        Wallet wallet = walletService.save(walletService.generateWallet(walletDto));

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
