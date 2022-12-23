package com.wallet.DigiPay.digiTest;

import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.entities.Wallet;

import java.util.UUID;

public class ModelsGenerate {

    public static User generateUser() {
        User user = new User();
        user.setRole(generateRole());
        user.setNationalCode("0080554113");
        user.setPhoneNumber("09127615507");
        user.setPassword("zj@zj@zj@ZJ");
        user.setId(10L);
        return user;
    }

    public static Role generateRole() {
        Role role = new Role();
        role.setRoleType(RoleType.User);
        role.setDescription("user can create wallet and do deposit, withdraw and transfer wallet to wallet");
        role.setId(1L);
        return role;
    }

    public static Wallet generateWallet(){
        Wallet wallet = new Wallet();
        wallet.setUser(generateUser());
        wallet.setActive(true);
        wallet.setBalance(500000.0);
        wallet.setTitle("pasandaz");
        wallet.setId(5L);
        wallet.setWalletNumber(UUID.randomUUID().toString());
        return wallet;
    }


}
