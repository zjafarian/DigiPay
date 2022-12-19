package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.exceptions.*;
import com.wallet.DigiPay.repositories.TransactionRepository;
import com.wallet.DigiPay.mapper.impl.RoleMapper;
import com.wallet.DigiPay.mapper.impl.UserMapper;
import com.wallet.DigiPay.mapper.impl.WalletMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.WalletRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;

import com.wallet.DigiPay.services.WalletService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class WalletServiceImpl extends BaseServiceImpl<Wallet, Long> implements WalletService {


    private WalletRepository walletRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private TransactionRepository transactionRepository;

    private WalletMapperImpl walletMapper;
    private UserMapper userMapper;
    private RoleMapper roleMapper;

    private ErrorMessages errorMessages;


    @Autowired
    public WalletServiceImpl(WalletRepository walletRepository,
                             UserRepository userRepository,
                             WalletMapperImpl walletMapper,
                             RoleRepository roleRepository,
                             TransactionRepository transactionRepository,
                             UserMapper userMapper,
                             RoleMapper roleMapper,
                             ErrorMessages errorMessages) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.transactionRepository = transactionRepository;
        this.walletMapper = walletMapper;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;

        this.errorMessages = errorMessages;
    }


    public Wallet generateWallet(WalletDto walletDto) {
        return walletMapper.mapToObject(walletDto);
    }

    public WalletDto generateWalletDto(Wallet wallet) {

        WalletDto walletDto = walletMapper.mapToDTO(wallet);
        Optional<User> user = userRepository.findById(wallet.getUser().getId());

        if (!user.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());

        List<Role> roles = roleRepository.findAllById(user.get()
                .getRoleDetails()
                .stream()
                .map(roleDetail -> roleDetail.getRoleDetailId().getRoleId())
                .collect(Collectors.toList()));

        UserDto userDto = userMapper.toUserDto(user.get(), roleMapper.toRoleDtos(roles));

        walletDto.setUser(userDto);

        return walletDto;
    }

    @Override
    protected BaseRepository<Wallet, Long> getBaseRepository() {
        return walletRepository;
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
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());

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
    @Override
    public Wallet depositWallet(Double amount, Long walletId) {

        Optional<Wallet> walletOptional = findById(walletId);
        if (!walletOptional.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());

        if (!walletOptional.get().getActive())
            throw new WalletActiveException(errorMessages.getMESSAGE_DE_ACTIVE_WALLET());


        if (amount < 0)
            throw new AmountException(errorMessages.getMESSAGE_LESS_THAN_ZERO_AMOUNT());

        if (amount == 0)
            throw new AmountException(errorMessages.getMESSAGE_ZERO_AMOUNT());

        Double balance = walletOptional.get().getBalance() + amount;
        Wallet wallet = walletOptional.get();
        wallet.setBalance(balance);

        return wallet;


    }

    @Override
    public Wallet withdrawWallet(Double amount, Long walletId) {

        Optional<Wallet> walletOptional = findById(walletId);
        if (!walletOptional.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_WALLET());

        if (!walletOptional.get().getActive())
            throw new WalletActiveException(errorMessages.getMESSAGE_DE_ACTIVE_WALLET());

        if (amount < 0)
            throw new AmountException(errorMessages.getMESSAGE_LESS_THAN_ZERO_AMOUNT());

        if (amount == 0)
            throw new AmountException(errorMessages.getMESSAGE_ZERO_AMOUNT());

        Double balance = walletOptional.get().getBalance() - amount;

        if (balance < 0)
            throw new AmountException(errorMessages.getMESSAGE_WALLET_BALANCE());

        Wallet wallet = walletOptional.get();

        wallet.setBalance(balance);




        return wallet;
    }

    @Override
    public void TransferFromWalletToWallet(Double amount, Long walletId) {

    }


}
