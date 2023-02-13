package com.management.usermanagement.service.user;

import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;
import com.management.usermanagement.domain.user.UserRepository;
import com.management.usermanagement.domain.user.exception.DuplicatedUserException;
import com.management.usermanagement.domain.user.exception.UserNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(SaveUserDto userDto) {
        if (emailAlreadyExists(userDto.email())) {
            throw new DuplicatedUserException();
        }

        String encryptedPassword = passwordEncoder.encode(userDto.password());

        User user = new User(
                userDto.email(),
                userDto.firstName(),
                userDto.lastName(),
                userDto.role(),
                encryptedPassword
        );

        return userRepository.save(user);
    }

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    private boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
