package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.TransactionStatus;
import com.wallet.DigiPay.exceptions.CartNumberException;
import com.wallet.DigiPay.exceptions.TransactionException;
import com.wallet.DigiPay.mapper.impl.TransactionMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.TransactionService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import com.wallet.DigiPay.utils.IGP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, Long> implements TransactionService {


    private TransactionRepository transactionRepository;
    private ErrorMessages errorMessages;
    private TransactionMapperImpl transactionMapper;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ErrorMessages errorMessages,
                                  TransactionMapperImpl transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.errorMessages = errorMessages;
    }


    public Transaction generateTransaction(TransactionRequestDto transactionRequestDto) {
        if (transactionRequestDto.getWallet() == null || transactionRequestDto.getTransactionType() == null)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());

       switch (transactionRequestDto.getTransactionType()){
           case Deposit: {
               if (transactionRequestDto.getSource() == null || transactionRequestDto.getSource().length() ==0)
                   throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());
           }
           break;

           case Withdraw: {
               if (transactionRequestDto.getDescription() == null ||
                       transactionRequestDto.getDescription().length() ==0)
                   throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());
           }
           break;
       }


        return transactionMapper.mapToObject(transactionRequestDto);
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

        switch (entity.getTransactionType()) {
            case Deposit: {
                //check source; before redirecting to IPG, check cart number(16 digits)
                if (entity.getSource().length() != 16) {
                    entity.setTransactionStatus(TransactionStatus.Failed);
                    transactionRepository.save(entity);
                    throw new CartNumberException(errorMessages.getMESSAGE_WRONG_CART_NUMBER());
                }


            }
            break;
            case Withdraw: {

                if (entity.getDescription().length() != 16) {
                    //check destination; before redirecting to IPG, check cart number(16 digits)
                    entity.setTransactionStatus(TransactionStatus.Failed);
                    transactionRepository.save(entity);
                    throw new CartNumberException(errorMessages.getMESSAGE_WRONG_CART_NUMBER());
                }


            }
            break;
        }


        if (IGP.failedStatusRedirectingToIGP) {
            entity.setTransactionStatus(TransactionStatus.Failed);
            transactionRepository.save(entity);
            throw new TransactionException(errorMessages.getMESSAGE_FAILED_TRANSACTION());
        }

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
