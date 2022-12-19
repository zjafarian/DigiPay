package com.wallet.DigiPay.mapper;


import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.Wallet;


public interface TransactionMapper extends BaseMapper<TransactionRequestDto, Transaction> {

}
