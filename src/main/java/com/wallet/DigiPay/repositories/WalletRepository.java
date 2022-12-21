package com.wallet.DigiPay.repositories;

import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.repositories.base.BaseRepository;

import java.util.Optional;

public interface WalletRepository extends BaseRepository<Wallet,Long> {

    Optional<Wallet> findByWalletNumber(String walletNumber);


}
