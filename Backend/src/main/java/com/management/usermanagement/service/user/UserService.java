package com.management.usermanagement.service.user;

import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;

public interface UserService {
    User saveUser(SaveUserDto userDto);
}
