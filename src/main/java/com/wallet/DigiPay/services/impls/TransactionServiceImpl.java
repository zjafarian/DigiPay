package com.wallet.DigiPay.services.impls;



import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.TransactionService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


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

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction save(Transaction entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public List<Transaction> saveAll(List<Transaction> entities) {
        return transactionRepository.saveAll(entities);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        transactionRepository.deleteById(id);
    }

    @Override
    public Transaction update(Transaction entity) {
        return transactionRepository.save(entity);
    }

    @Override
    public List<Transaction> findAllById(Iterable<Long> ids) {
        return transactionRepository.findAllById(ids);
    }
}
