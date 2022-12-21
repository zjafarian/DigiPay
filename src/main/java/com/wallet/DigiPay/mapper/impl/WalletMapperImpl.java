package com.wallet.DigiPay.mapper.impl;

import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.mapper.WalletMapper;
import org.springframework.stereotype.Component;

@Component
public class WalletMapperImpl extends BaseMapperImpl<WalletDto, Wallet> implements WalletMapper {

    @Override
    public WalletDto mapToDTO(Wallet wallet) {
        WalletDto walletDto = new WalletDto();
        walletDto.setId(wallet.getId());
        walletDto.setBalance(wallet.getBalance());
        walletDto.setWalletNumber(wallet.getWalletNumber());
        walletDto.setIsActive(wallet.getActive());
        walletDto.setUserId(wallet.getUser().getId());
        walletDto.setTitle(wallet.getTitle());


        return walletDto;
    }

    @Override
    public Wallet mapToObject(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setBalance(walletDto.getBalance());
        wallet.setTitle(walletDto.getTitle());

        if (walletDto.getId() == null){

            wallet.setDeleted(false);
            wallet.setActive(true);
        }

        wallet.setActive(walletDto.getIsActive());


        return wallet;
    }
}
