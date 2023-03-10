package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.TransactionStatus;
import com.wallet.DigiPay.exceptions.CartNumberException;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.exceptions.TransactionException;
import com.wallet.DigiPay.mapper.impl.TransactionMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.WalletRepository;
import com.wallet.DigiPay.base.baseRepository.BaseRepository;
import com.wallet.DigiPay.services.TransactionService;
import com.wallet.DigiPay.base.baseServide.impls.BaseServiceImpl;
import com.wallet.DigiPay.utils.IGP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TransactionServiceImpl extends BaseServiceImpl<Transaction, Long> implements TransactionService {


    private TransactionRepository transactionRepository;
    private WalletRepository walletRepository;
    private ErrorMessages errorMessages;
    private TransactionMapperImpl transactionMapper;


    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  ErrorMessages errorMessages,
                                  WalletRepository walletRepository,
                                  TransactionMapperImpl transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.walletRepository = walletRepository;
        this.transactionMapper = transactionMapper;
        this.errorMessages = errorMessages;
    }


    public Transaction generateTransaction(TransactionRequestDto transactionRequestDto) {

        if (transactionRequestDto.getWalletId() == null || transactionRequestDto.getTransactionType() == null)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());

        switch (transactionRequestDto.getTransactionType()) {
            case Deposit: {
                if (transactionRequestDto.getSource() == null || transactionRequestDto.getSource().length() == 0)
                    throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());
            }
            break;

            case Withdraw: {
                checkDestination(transactionRequestDto);
            }
            break;

            case TransferWTW_Deposit: {
                checkDestination(transactionRequestDto);
            }

            break;

            case TransferWTW_Withdraw: {
                checkDestination(transactionRequestDto);
            }


        }




        return transactionMapper.mapToObject(transactionRequestDto);
    }

    private void checkDestination(TransactionRequestDto transactionRequestDto) {
        if (transactionRequestDto.getDestination() == null ||
                transactionRequestDto.getDestination().length() == 0)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());
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

                if (entity.getDestination().length() != 16) {
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


    @Override
    public List<Transaction> getTransactions(Long walletId) {

        if (!walletRepository.findById(walletId).isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());


        return transactionRepository.findByWalletId(walletId);
    }
}
