package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.WalletRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;

import com.wallet.DigiPay.services.WalletService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class WalletServiceImpl extends BaseServiceImpl<Wallet,Long> implements WalletService {


    private WalletRepository walletRepository;


    public WalletServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    protected BaseRepository<Wallet, Long> getBaseRepository() {
        return walletRepository;
    }
}
