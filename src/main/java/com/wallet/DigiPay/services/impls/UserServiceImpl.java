package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.*;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.repositories.base.BaseRepository;
import com.wallet.DigiPay.services.UserService;
import com.wallet.DigiPay.services.base.impls.BaseServiceImpl;
import com.wallet.DigiPay.utils.NationalCodeValidation;
import com.wallet.DigiPay.utils.PasswordValidation;
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


        //check pattern of nationalCode
        if (!NationalCodeValidation.validationNationalCode(entity.getNationalCode()))
            throw new NationalCodeException(errorMessages.getMESSAGE_NOT_VALID_NATIONAL_CODE());

        //check password is valid or not
        if (!PasswordValidation.validationPassword(entity.getPassword()))
            throw new PasswordException(errorMessages.getMESSAGE_PASSWORD_NOT_VALID());


        return super.save(entity);
    }

    @Override
    public List<User> saveAll(List<User> entities) {
        return super.saveAll(entities);
    }

    @Override
    public Optional<User> findById(Long id) {

        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());


        return user;
    }

    @Override
    public void delete(Long aLong) {
        super.delete(aLong);
    }

    @Override
    public User update(User entity) {
        return super.update(entity);
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }
}
