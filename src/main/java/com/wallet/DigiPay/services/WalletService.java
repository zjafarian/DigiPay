package com.wallet.DigiPay.services;

import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.services.base.BaseService;

public interface WalletService extends BaseService<Wallet,Long> {


    //Deposit money from the bank to the wallet
    Wallet depositWallet(Double amount, Long walletId);

    //Withdraw money to the bank from the wallet
    Wallet withdrawWallet(Double amount,Long walletId);

    //Transfer money from one wallet to another wallet
    void TransferFromWalletToWallet(Double amount, Long walletId);




}
