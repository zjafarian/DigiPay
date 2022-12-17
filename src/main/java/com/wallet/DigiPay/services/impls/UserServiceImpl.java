package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.UserService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {


    private UserRepository userRepository;


    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected BaseRepository<User, Long> getBaseRepository() {
        return userRepository;
    }
}
