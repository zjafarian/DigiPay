package com.wallet.DigiPay.services.impls;

import com.wallet.DigiPay.dto.TransactionRequestDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Transaction;
import com.wallet.DigiPay.entities.TransactionType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.exceptions.*;
import com.wallet.DigiPay.mapper.impl.TransactionMapperImpl;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.mapper.impl.RoleMapperImpl;
import com.wallet.DigiPay.mapper.impl.UserMapperImpl;
import com.wallet.DigiPay.mapper.impl.WalletMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.WalletRepository;
import com.wallet.DigiPay.base.baseRepository.BaseRepository;
import com.wallet.DigiPay.services.WalletService;
import com.wallet.DigiPay.base.baseServide.impls.BaseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service

public class WalletServiceImpl extends BaseServiceImpl<Wallet, Long> implements WalletService {


    private WalletRepository walletRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TransactionRepository transactionRepository;
    private TransactionMapperImpl transactionMapper;

    private WalletMapperImpl walletMapper;
    private UserMapperImpl userMapper;
    private RoleMapperImpl roleMapper;

    private ErrorMessages errorMessages;


    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository,
                             UserRepository userRepository,
                             WalletMapperImpl walletMapper,
                             RoleRepository roleRepository,
                             TransactionRepository transactionRepository,
                             TransactionMapperImpl transactionMapper,
                             UserMapperImpl userMapper,
                             RoleMapperImpl roleMapper,
                             ErrorMessages errorMessages) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.walletMapper = walletMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.transactionMapper = transactionMapper;
        this.errorMessages = errorMessages;
    }

    @Override
    protected BaseRepository<Wallet, Long> getBaseRepository() {
        return walletRepository;
    }


    public Wallet generateWallet(WalletDto walletDto) {
        User user = findUser(walletDto.getUserId()).get();

        Wallet wallet = walletMapper.mapToObject(walletDto);


        wallet.setUser(user);

        return wallet;
    }


    public WalletDto generateWalletDto(Wallet wallet) {

        WalletDto walletDto = walletMapper.mapToDTO(wallet);
        findUser(wallet.getUser().getId());

        return walletDto;
    }



    @NotNull
    private Optional<User> findUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());

        return user;
    }


    @Override
    public List<Wallet> findAll() {
        return super.findAll();
    }

    @Override
    public Wallet save(Wallet entity) {

        //check title of wallet is null,empty or blank
        if (entity.getTitle().isEmpty() || entity.getTitle().isBlank() || entity.getTitle() == null)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());

        if (entity.getBalance() < 0)
            throw new AmountException(errorMessages.getMESSAGE_LESS_THAN_ZERO_AMOUNT());

        if (entity.getBalance() == null)
            entity.setBalance(0.0);


        return walletRepository.save(entity);
    }

    @Override
    public List<Wallet> saveAll(List<Wallet> entities) {
        return super.saveAll(entities);
    }

    @Override
    public Optional<Wallet> findById(Long id) {


        Optional<Wallet> wallet = walletRepository.findById(id);
        if (!wallet.isPresent())
            notFoundWallet(errorMessages.getMESSAGE_NOT_FOUND_WALLET());

        return wallet;
    }

    @Override
    public void delete(Long id) {

        if (!walletRepository.findById(id).isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());


        walletRepository.deleteById(id);
    }

    @Override
    public Wallet update(Wallet entity) {

        if (!walletRepository.findById(entity.getId()).isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());


        return walletRepository.save(entity);
    }

    @Override
    public List<Wallet> findAllById(Iterable<Long> longs) {
        return super.findAllById(longs);
    }


    //Deposit money from the bank to the wallet

    public Wallet depositWallet(Double amount, Wallet wallet) {


        if (amount < 0)
            throw new AmountException(errorMessages.getMESSAGE_LESS_THAN_ZERO_AMOUNT());

        if (amount == 0)
            throw new AmountException(errorMessages.getMESSAGE_ZERO_AMOUNT());

        Double balance = wallet.getBalance() + amount;

        wallet.setBalance(balance);

        return wallet;


    }


    public Wallet withdrawWallet(Double amount, Wallet wallet) {



        checkAmount(amount);

        Double balance = calculateWithdraw.calculateWithdraw(wallet.getBalance(), amount);

        checkBalance(balance);



        wallet.setBalance(balance);


        return wallet;
    }

    //when user wants to transfer money from a wallet to another wallet

    public List<Wallet> transferFromWalletToWallet(Double amount,
                                                   List<Wallet> wallets) {


        //find wallet source with walletId
        Wallet walletSource = wallets.get(0);

        //find wallet destination with walletNumber

        Wallet walletDestination = wallets.get(1);




        //check source wallet is active or not
        if (!walletSource.getActive())
            deActiveWallet(walletSource.getWalletNumber() + ": " + errorMessages.getMESSAGE_DE_ACTIVE_WALLET());




        //check destination wallet is active or not
        if (!walletDestination.getActive())
            deActiveWallet(walletDestination.getWalletNumber() + ": " + errorMessages.getMESSAGE_DE_ACTIVE_WALLET());

        //check wallets' numbers won't be equal
        if (Objects.equals(walletSource.getWalletNumber() , walletDestination.getWalletNumber()))
            throw new WalletNumberException(errorMessages.getMESSAGE_WALLETS_NUMBERS_COULD_NOT_EQUAL());


        //check amount
        checkAmount(amount);

        //calculate balance form source wallet
        Double balance = calculateWithdraw.calculateWithdraw(walletSource.getBalance(), amount);

        //balance of source wallet after calculating will check, if it will be less than zero, user could not transfer money
        checkBalance(balance);


        walletSource.setBalance(balance);

        walletDestination.setBalance(walletDestination.getBalance() + amount);

        return Arrays.asList(walletSource, walletDestination);

    }


    public Wallet changeActiveWallet(WalletDto wallet) {
        Wallet walletFind = findById(wallet.getId()).get();
        walletFind.setActive(wallet.getIsActive());



        return walletFind;
    }



    public Transaction deposit(TransactionRequestDto transactionRequestDto){

        Wallet walletFind = findById(transactionRequestDto.getWalletId()).get();

        Wallet wallet = depositWallet(transactionRequestDto.getAmount(), walletFind);

        transactionRequestDto.setSource(wallet.getWalletNumber());

        walletRepository.save(wallet);

        return createTransaction(Arrays.asList(wallet), transactionRequestDto).get(0);

    }


    public Transaction withdraw(TransactionRequestDto transactionRequestDto){
        Wallet walletFind = findById(transactionRequestDto.getWalletId()).get();


        Wallet wallet = withdrawWallet(transactionRequestDto.getAmount(), walletFind);

        transactionRequestDto.setDestination(wallet.getWalletNumber());



        return createTransaction(Arrays.asList(wallet), transactionRequestDto).get(0);
    }


    public List<Transaction> walletToWallet(TransactionRequestDto transactionRequestDto){
        Wallet walletSource = findById(transactionRequestDto.getWalletId()).get();
        Wallet walletDestination = findWalletByNumber(transactionRequestDto.getDestination()).get();


        List<Wallet> wallets = transferFromWalletToWallet(transactionRequestDto.getAmount(),
                Arrays.asList(walletSource, walletDestination));

        transactionRequestDto.setSource(wallets.get(0).getWalletNumber());

        return createTransaction(wallets, transactionRequestDto);

    }


    public Wallet returnWallet(Transaction transaction){

        Wallet wallet = findById(transaction.getWallet().getId()).get();

        return walletRepository.save(wallet);
    }





    //create transactions with wallets and transactionRequestDto
    public List<Transaction> createTransaction(List<Wallet> wallets, TransactionRequestDto transactionRequestDto) {

        //check transaction types
        if (Objects.equals(transactionRequestDto.getTransactionType(), TransactionType.Deposit) ||
                Objects.equals(transactionRequestDto.getTransactionType(), TransactionType.Withdraw)) {

            //create transaction for deposit or withdraw
            Transaction transaction = transactionMapper.mapToObject(transactionRequestDto);
            transaction.setWalletBalance(wallets.get(0).getBalance());
            transaction.setWallet(wallets.get(0));
            transaction.setUser(wallets.get(0).getUser());

            return Arrays.asList(transaction);

        } else if (Objects.equals(transactionRequestDto.getTransactionType(), TransactionType.TransferWTW)) {

            //create transactions for transfer money from a wallet to another wallet
            transactionRequestDto.setTransactionType(TransactionType.TransferWTW_Withdraw);

            Transaction transaction1 = transactionMapper.mapToObject(transactionRequestDto);
            transaction1.setWalletBalance(wallets.get(0).getBalance());
            transaction1.setUser(wallets.get(0).getUser());
            transaction1.setWallet(wallets.get(0));

            transactionRequestDto.setTransactionType(TransactionType.TransferWTW_Deposit);
            Transaction transaction2 = transactionMapper.mapToObject(transactionRequestDto);
            transaction2.setWalletBalance(wallets.get(1).getBalance());
            transaction2.setWallet(wallets.get(1));
            transaction2.setUser(wallets.get(1).getUser());

            return Arrays.asList(transaction1, transaction2);


        }

        return new ArrayList<>();

    }


    private CalculateWithdraw<Double> calculateWithdraw = (a, b) -> a - b;

    private void checkAmount(Double amount) {

        if (amount < 0)
            throw new AmountException(errorMessages.getMESSAGE_LESS_THAN_ZERO_AMOUNT());

        if (amount == 0)
            throw new AmountException(errorMessages.getMESSAGE_ZERO_AMOUNT());
    }

    private void checkBalance(Double balance) {
        if (balance < 0)
            throw new AmountException(errorMessages.getMESSAGE_WALLET_BALANCE());
    }

    public Optional<Wallet> findWalletByNumber(String walletNumber){
        Optional<Wallet> wallet = walletRepository.findByWalletNumber(walletNumber);

        if (!wallet.isPresent())
            notFoundWallet(errorMessages.getMESSAGE_NOT_FOUND_WALLET());

        return wallet;
    }

    private void notFoundWallet(String message) {
        throw new NotFoundException(message);
    }

    private void deActiveWallet(String message) {
        throw new WalletActiveException(message);
    }




}


@FunctionalInterface
interface CalculateWithdraw<T> {
    T calculateWithdraw(T a, T b);
}
