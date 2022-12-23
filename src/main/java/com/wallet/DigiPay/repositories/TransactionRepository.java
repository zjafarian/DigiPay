package com.wallet.DigiPay.repositories;

import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.base.baseRepository.BaseRepository;

import java.util.List;

public interface TransactionRepository extends BaseRepository<Transaction,Long> {


    List<Transaction> findByWalletId(Long id);



}
