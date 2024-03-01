package com.bankApp.bankApp.Service;

import com.bankApp.bankApp.CustomException.InvalidEmailException;
import com.bankApp.bankApp.CustomException.InvalidPasswordException;
import com.bankApp.bankApp.Model.User;
import com.bankApp.bankApp.Repository.AccountRepository;
import com.bankApp.bankApp.Repository.UserRepository;
import com.bankApp.bankApp.Service.UserService;
import com.bankApp.bankApp.Utili.DTO.LoginCred;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testRegisterUser_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        assertNotNull(registeredUser);
        assertEquals(user.getEmail(), registeredUser.getEmail());
    }

    @Test
    void testRegisterUser_ExistingEmail() {
        User user = new User();
        user.setEmail("existing@example.com");

        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        assertThrows(InvalidEmailException.class, () -> userService.registerUser(user));
    }


}
