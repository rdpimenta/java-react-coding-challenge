package com.management.usermanagement.controller.user;

import com.management.usermanagement.domain.user.SaveUserDto;
import com.management.usermanagement.domain.user.User;
import com.management.usermanagement.domain.user.UserDto;
import com.management.usermanagement.domain.user.UserRepository;
import com.management.usermanagement.service.user.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> saveUser(
            @RequestBody
            @Valid
            SaveUserDto saveUserDto
    ) {
        User user = userService.saveUser(saveUserDto);
        UserDto userDto = new UserDto(user);

        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDto> getUserByEmail(
            @PathVariable
            String email
    ) {
        User user = userService.getUserByEmail(email);
        UserDto dto = new UserDto(user);

        return ResponseEntity.ok(dto);
    }
}
