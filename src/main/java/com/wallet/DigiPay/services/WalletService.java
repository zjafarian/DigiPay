package com.wallet.DigiPay.services;

import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.base.baseServide.BaseService;

import java.util.List;

public interface WalletService extends BaseService<Wallet,Long> {


    //Deposit money from the bank to the wallet
    Wallet depositWallet(Double amount, Wallet wallet);

    //Withdraw money to the bank from the wallet
    Wallet withdrawWallet(Double amount,Wallet wallet);

    //Transfer money from one wallet to another wallet
    List<Wallet> transferFromWalletToWallet(Double amount,
                                            List<Wallet> wallets);


    Wallet changeActiveWallet(Wallet wallet);








}
