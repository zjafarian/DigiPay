package com.wallet.DigiPay.services.impls;



import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.TransactionService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction,Long> implements TransactionService {


    private TransactionRepository transactionRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    protected BaseRepository<Transaction, Long> getBaseRepository() {
        return transactionRepository;
    }
}
