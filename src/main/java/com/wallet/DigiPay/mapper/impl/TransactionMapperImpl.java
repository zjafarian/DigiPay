package com.wallet.DigiPay.mapper.impl;

import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.TransactionStatus;
import com.wallet.DigiPay.entities.TransactionType;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.mapper.TransactionMapper;
import com.wallet.DigiPay.mapper.WalletMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl extends BaseMapperImpl<TransactionRequestDto, Transaction> implements TransactionMapper {

    @Override
    public TransactionRequestDto mapToDTO(Transaction transaction) {
        return super.mapToDTO(transaction);
    }

    @Override
    public Transaction mapToObject(TransactionRequestDto transactionRequestDto) {
        Transaction transaction = new Transaction();
        transaction.setDescription(transactionRequestDto.getDescription());
        transaction.setTransactionType(transactionRequestDto.getTransactionType());
        transaction.setTransactionStatus(TransactionStatus.Init);
        transaction.setAmount(transactionRequestDto.getAmount());
        transaction.setSource(transactionRequestDto.getSource());
        transaction.setDescription(transaction.getDescription());

        transaction.setDeleted(false);


        return transaction;


    }
}
