package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.*;
import com.wallet.DigiPay.mapper.impl.RoleMapperImpl;
import com.wallet.DigiPay.mapper.impl.UserMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
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
    private RoleRepository roleRepository;

    private UserMapperImpl userMapper;
    private RoleMapperImpl roleMapper;

    private ErrorMessages errorMessages;




    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapperImpl userMapper,
                           RoleMapperImpl roleMapper,
                           ErrorMessages errorMessages) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.errorMessages =errorMessages;

    }


    public UserRequestDto generateUserRequestDto(User user){

        return userMapper.mapToDTO(user);
    }


    public User generateUser(UserRequestDto userRequestDto){

        if (userRequestDto.getRoleId() == null)
            throw new NullPointerException(errorMessages.getMESSAGE_NULL_ENTRY());



        return userMapper.mapToObject(userRequestDto);
    }


    public Optional<User> findUserByNationalCode(String nationalCode){
        Optional<User> user = userRepository.findByNationalCode(nationalCode);


        return user;
    }




    @Override
    protected BaseRepository<User, Long> getBaseRepository() {
        return userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User entity) {


        //check pattern of phone number
        if (!PhoneNumberValidation.validationPhoneNumber(entity.getPhoneNumber()))
            throw new ValidationPhoneNumberException(errorMessages.getMESSAGE_PHONE_IS_NOT_CORRECT());

        //check national code of entity is existed in database or not
        if (userRepository.findByNationalCode(entity.getPhoneNumber()).isPresent())
            throw new ExistNationalCodeException(errorMessages.getMESSAGE_NATIONAL_CODE_IS_EXIST());


        //check pattern of nationalCode
        if (!NationalCodeValidation.validationNationalCode(entity.getNationalCode()))
            throw new NationalCodeException(errorMessages.getMESSAGE_NOT_VALID_NATIONAL_CODE());




        if (entity.getRole() == null)
            throw new PasswordException(errorMessages.getMESSAGE_NOT_FOUND_ROLE());




        return userRepository.save(entity);
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
    public void delete(Long id) {
        if (!userRepository.findById(id).isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());


        userRepository.deleteById(id);
    }

    @Override
    public User update(User entity) {
        if (!userRepository.findById(entity.getId()).isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());

        return userRepository.save(entity);
    }

    @Override
    public List<User> findAllById(Iterable<Long> ids) {
        return userRepository.findAllById(ids);
    }

    public Role findRole(Long roleId) {

        Optional<Role> role = roleRepository.findById(roleId);
        if (!role.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_ROLE());

        return role.get();
    }
}
