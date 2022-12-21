package com.wallet.DigiPay.services;


import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.services.base.BaseService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService extends BaseService<Transaction,Long> {

    List<Transaction> getTransactions(Long walletId);

}
