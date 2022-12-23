package com.wallet.DigiPay.services;


import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.base.baseServide.BaseService;

import java.util.List;

public interface TransactionService extends BaseService<Transaction,Long> {

    List<Transaction> getTransactions(Long walletId);

}
