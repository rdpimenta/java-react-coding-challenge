package com.management.usermanagement.service.user;

import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;
import com.management.usermanagement.domain.user.UserDto;

public interface UserService {
    User saveUser(SaveUserDto userDto);
    User getUserByEmail(String email);
}
