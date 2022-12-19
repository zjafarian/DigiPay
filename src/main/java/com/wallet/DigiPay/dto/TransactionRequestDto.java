package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.TransactionType;
import com.wallet.DigiPay.entities.Wallet;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TransactionRequestDto {

    @NotNull
    private WalletDto wallet;

    private String Description;

    private String source;

    private String destination;

    private Double amount;

    @NotNull
    private TransactionType transactionType;


}
