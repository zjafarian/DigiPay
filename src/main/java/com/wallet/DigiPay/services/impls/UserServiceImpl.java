package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistPhoneNumberException;
import com.wallet.DigiPay.exceptions.ValidationPhoneNumberException;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.UserService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import com.wallet.DigiPay.utils.PhoneNumberValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl extends BaseServiceImpl<User,Long> implements UserService {


    private UserRepository userRepository;


    private ErrorMessages errorMessages;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, ErrorMessages errorMessages) {
        this.userRepository = userRepository;
        this.errorMessages =errorMessages;
    }

    @Override
    protected BaseRepository<User, Long> getBaseRepository() {
        return userRepository;
    }

    @Override
    public List<User> findAll() {
        return super.findAll();
    }

    @Override
    public User save(User entity) {

        //check pattern of phone number
        if (!PhoneNumberValidation.validationPhoneNumber(entity.getPhoneNumber()))
            throw new ValidationPhoneNumberException(errorMessages.getMESSAGE_PHONE_IS_NOT_CORRECT());

        //check phone number of entity is existed in database or not
        if (userRepository.findByPhoneNumber(entity.getPhoneNumber()).isPresent())
            throw new ExistPhoneNumberException(errorMessages.getMESSAGE_PHONE_IS_EXISTED());


        //


        return super.save(entity);
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return super.saveAll(entities);
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return super.findById(aLong);
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    public User update(User entity) {
        return super.update(entity);
    }
}
