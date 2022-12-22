package com.wallet.DigiPay.digiTest;

import com.wallet.DigiPay.DigiPayApplication;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DigiPayApplication.class)
public class digiPayTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;


    @DisplayName("test method save user")
    @Test
    public void should_When_Save_User_In_User_Service_And_Return_Entity() throws Exception {
        User user = new User();
        user.setPhoneNumber("09127615507");
        user.setNationalCode("0080554113");
        user.setPassword("zj@50454@ZJ");
        user.setRole(new Role(null, RoleType.User));

       User userSave = userService.save(user);
        assertThat(userSave).isNotNull();
    }

    @DisplayName("test method save when null")
    @Test
    public void should_When_Pars_Null_For_Save_Entity_In_User_Service_And_Return_Entity_With_Null() {
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class,
                () -> userService.save(null));

    }

    @Test
    public void update_Entity_Service() throws Exception {
        User user = new User();

        user.setName("zeinab");
        user.setPhoneNumber("09332844658");
        user.setNationalCode("0023518723");
        user.setPassword("z@505050@J");
        user.setRole(new Role(null,RoleType.User));

        User userSave = userService.save(user);
        System.out.println(userSave.getNationalCode());
        System.out.println(userSave.getPhoneNumber());
        userSave.setName("zeinab");
        userSave.setLastName("jafarian");
        User userSave2 = userService.save(userSave);
        System.out.println(userSave2.getNationalCode());
        System.out.println(userSave2.getLastName());
        Assertions.assertTrue(userSave2.getId() == userSave.getId());
    }

    @Test
    public void should_Return_Throw_Exception_Not_Found_Entity() {
        Assertions.assertThrows(NotFoundException.class,
                () -> userService.findById(200L));
    }




    @Test
    public void should_When_Username_And_Password_Return_Entity() throws Exception {
        User user = new User();

        user.setName("zeinab");
        user.setPhoneNumber("09332844658");
        user.setNationalCode("0023518723");
        user.setPassword("z@505050@J");
        user.setRole(new Role(null,RoleType.User));
        User userSave = userService.save(user);
        Assertions.assertAll(
                () -> Assertions.assertEquals(userSave.getPassword(), "09332844658"),
                () -> Assertions.assertEquals(userSave.getPassword(), "z@505050@J")
        );
    }

}
