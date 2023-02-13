package com.management.usermanagement.service.user;

import com.management.usermanagement.domain.user.Role;
import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;
import com.management.usermanagement.domain.user.UserRepository;
import com.management.usermanagement.domain.user.exception.DuplicatedUserException;
import com.management.usermanagement.domain.user.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
    @Mock
    private static UserRepository userRepository;
    @Mock
    private static PasswordEncoder passwordEncoder;
    private static UserService userService;
    private static String userEmail;
    private static User user;
    private static SaveUserDto saveUserDto;
    @BeforeAll
    static void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserServiceImpl(userRepository, passwordEncoder);
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        userEmail = "test@email.com";
        user = new User(
                userEmail,
                "firstName",
                "lastName",
                Role.ADMINISTRATOR,
                "encryptedPassword"
        );
        saveUserDto = new SaveUserDto(
                userEmail,
                "firstName",
                "lastName",
                Role.ADMINISTRATOR,
                "password"
        );

    }

    @Test
    public void shouldSaveUser() {
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(null);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);

        User userStored = userService.saveUser(saveUserDto);

        Assertions.assertEquals(user.getEmail(), userStored.getEmail());
        Assertions.assertEquals(user.getFirstName(), userStored.getFirstName());
        Assertions.assertEquals(user.getLastName(), userStored.getLastName());
        Assertions.assertEquals(user.getRole(), userStored.getRole());
    }

    @Test
    public void shouldNotSaveUserIfEmailAlreadyExists() {
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(user);

        Assertions.assertThrows(DuplicatedUserException.class, () -> userService.saveUser(saveUserDto));
    }

    @Test
    public void shouldGetUserByEmail() {
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(user);
        User userFound = userService.getUserByEmail(userEmail);

        Assertions.assertEquals(user.getEmail(), userFound.getEmail());
        Assertions.assertEquals(user.getFirstName(), userFound.getFirstName());
        Assertions.assertEquals(user.getLastName(), userFound.getLastName());
        Assertions.assertEquals(user.getRole(), userFound.getRole());
    }

    @Test
    public void shouldThrowExceptionWhenUserIsNotFoundByEmail() {
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(null);

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail(userEmail));
    }
}
