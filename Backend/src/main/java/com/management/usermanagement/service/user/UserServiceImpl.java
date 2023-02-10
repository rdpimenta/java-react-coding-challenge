package com.management.usermanagement.service.user;

import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;
import com.management.usermanagement.domain.user.UserRepository;
import com.management.usermanagement.domain.user.exception.DuplicatedUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

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

    private boolean emailAlreadyExists(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
