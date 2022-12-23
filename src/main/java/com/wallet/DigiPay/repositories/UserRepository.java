package com.wallet.DigiPay.repositories;


import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.repositories.base.BaseRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<User,Long> {

    //find by user with phone number
    Optional<User> findByPhoneNumber(String phoneNumber);


    Optional<User> findByNationalCode(String nationalCode);

    @Override
    List<User> findAll();
}
