package com.wallet.DigiPay.digiTest;


import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.mapper.impl.RoleMapperImpl;
import com.wallet.DigiPay.mapper.impl.TransactionMapperImpl;
import com.wallet.DigiPay.mapper.impl.UserMapperImpl;
import com.wallet.DigiPay.mapper.impl.WalletMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.WalletRepository;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import com.wallet.DigiPay.services.impls.WalletServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

;

@RunWith(MockitoJUnitRunner.class)
public class WalletServiceTest {

    @Mock
    private WalletRepository walletRepository;

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private TransactionRepository transactionRepository;

    private TransactionMapperImpl transactionMapper;
    private WalletMapperImpl walletMapper;
    private UserMapperImpl userMapper;
    private RoleMapperImpl roleMapper;
    private ErrorMessages errorMessages;


    private AutoCloseable closeable;


    private WalletServiceImpl walletService;

    @Before
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);

        transactionMapper = new TransactionMapperImpl();
        walletMapper = new WalletMapperImpl();
        userMapper = new UserMapperImpl();
        roleMapper = new RoleMapperImpl();
        errorMessages = new ErrorMessages();


        walletService = new WalletServiceImpl(walletRepository,
                userRepository,
                walletMapper,
                roleRepository,
                transactionRepository,
                transactionMapper,
                userMapper,
                roleMapper,
                errorMessages);

    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }


    @Test
    public void should_When_Save_Wallet_To_Database() throws Exception {
        Wallet wallet = ModelsGenerate.generateWallet();

        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());

        Wallet walletSave = walletService.save(wallet);


        assertThat(walletSave).isNotNull();
    }


    @Test
    public void should_When_Check_User_With_User_Of_Wallet() throws Exception {
        User user = ModelsGenerate.generateUser();
        Wallet wallet = ModelsGenerate.generateWallet();

        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User userSave = userRepository.save(user);

        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());
        Wallet walletSave = walletService.save(wallet);


        assertThat(userSave.getId()).isEqualTo(walletSave.getUser().getId());

    }


    @Test
    public void should_When_Deposit_To_Wallet() throws Exception {
        User user = ModelsGenerate.generateUser();
        Wallet wallet = ModelsGenerate.generateWallet();

        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User userSave = userRepository.save(user);

        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());
        Wallet walletSave = walletService.save(wallet);

        Double amount = 10000D;
        Double balance = walletSave.getBalance();

        Wallet walletAfterDeposit = walletService.depositWallet(amount, walletSave);


        assertThat(walletAfterDeposit.getBalance()).isEqualTo(amount + balance);

    }

    @Test
    public void should_When_Withdraw_From_Wallet() throws Exception {
        User user = ModelsGenerate.generateUser();
        Wallet wallet = ModelsGenerate.generateWallet();

        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User userSave = userRepository.save(user);

        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());
        Wallet walletSave = walletService.save(wallet);

        Double amount = 500D;
        Double balance = walletSave.getBalance();

        Wallet walletAfterDeposit = walletService.withdrawWallet(amount, walletSave);


        assertThat(walletAfterDeposit.getBalance()).isEqualTo( balance - amount);

    }


    @Test
    public void should_When_Transfer_From_Wallet_To_Wallet() throws Exception {
        User user = ModelsGenerate.generateUser();
        Wallet walletSource = ModelsGenerate.generateWallet();

        Wallet walletDestination = new Wallet();
        walletDestination.setId(20L);
        walletDestination.setUser(user);
        walletDestination.setWalletNumber(UUID.randomUUID().toString());
        walletDestination.setBalance(5000D);
        walletDestination.setActive(true);
        walletDestination.setTitle("gholak");


        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        User userSave = userRepository.save(user);

        when(walletRepository.save(any(Wallet.class))).then(returnsFirstArg());
        Wallet walletSaveSource = walletService.save(walletSource);

        Wallet walletSaveDestination = walletService.save(walletDestination);

        Double amount = 500D;
        Double balanceResultSource = walletSaveSource.getBalance() - amount;


        Double balanceResultDestination = walletDestination.getBalance() + amount;

        List<Wallet> wallets = walletService.transferFromWalletToWallet(amount, Arrays.asList(walletSaveSource,walletSaveDestination));


        assertThat(wallets.get(0).getBalance()).isEqualTo(balanceResultSource);
        assertThat(wallets.get(1).getBalance()).isEqualTo(balanceResultDestination);

    }


}
