package com.wallet.DigiPay.mapper.impl;

import com.wallet.DigiPay.dto.WalletDto;
import com.wallet.DigiPay.entities.Wallet;
import com.wallet.DigiPay.mapper.WalletMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class WalletMapperImpl extends BaseMapperImpl<WalletDto, Wallet> implements WalletMapper {

    @Override
    public WalletDto mapToDTO(Wallet wallet) {
        return super.mapToDTO(wallet);
    }

    @Override
    public Wallet mapToObject(WalletDto walletDto) {
        Wallet wallet = new Wallet();
        wallet.setBalance(walletDto.getBalance());
        wallet.setTitle(walletDto.getTitle());

        if (walletDto.getId() == null){
            wallet.setDateCreated(new Timestamp(System.currentTimeMillis()));
            wallet.setDeleted(false);
            wallet.setActive(true);
        }

        wallet.setDateModified(new Timestamp(System.currentTimeMillis()));
        wallet.setActive(walletDto.getIsActive());


        return wallet;
    }
}
