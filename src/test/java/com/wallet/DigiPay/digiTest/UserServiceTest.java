package com.wallet.DigiPay.digiTest;


import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.mapper.impl.RoleMapperImpl;
import com.wallet.DigiPay.mapper.impl.UserMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock

    private RoleRepository roleRepository;

    private AutoCloseable closeable;


    private UserMapperImpl userMapper;


    private RoleMapperImpl roleMapper;


    private ErrorMessages errorMessages;


    private UserServiceImpl userService;

    @Before
    public void init() {
        closeable = MockitoAnnotations.openMocks(this);

        userMapper = new UserMapperImpl();
        roleMapper = new RoleMapperImpl();
        errorMessages = new ErrorMessages();


        userService = new UserServiceImpl(userRepository, roleRepository, userMapper, roleMapper, errorMessages);

    }

    @AfterEach
    void closeService() throws Exception {
        closeable.close();
    }



    @Test
    public void should_When_Save_User_In_User_Service_And_Return_Entity() throws Exception {
        User user = ModelsGenerate.generateUser();



        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        User userSave = userService.save(user);


        assertThat(userSave).isNotNull();
    }


    @Test
    public void should_When_Pars_Null_For_Save_Entity_In_User_Service_And_Return_Entity_With_Null() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> userService.save(null));
    }

    @Test
    public void update_Entity_Service() throws Exception {

        User user = ModelsGenerate.generateUser();

        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        User userSave = userService.save(user);



        userSave.setName("zeinab");
        userSave.setLastName("jafarian");

        when(userRepository.save(any(User.class))).then(returnsFirstArg());

        User userSave2 = userService.save(userSave);



        Assertions.assertTrue(userSave2.getId() == userSave.getId());
    }

    @Test
    public void should_Return_Throw_Exception_Not_Found_Entity() {
        Assertions.assertThrows(NotFoundException.class,
                () -> userService.findById(200L));
    }



    @Test
    public void getAllUsers() {
        userService.findAll();
        verify(userRepository).findAll();
    }

}
