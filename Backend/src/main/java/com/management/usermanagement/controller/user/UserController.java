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

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

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

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> userList = userRepository.findAll();
        List<UserDto> userDtoList = userList.stream().map(UserDto::new).toList();

        return ResponseEntity.ok(userDtoList);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserDto> editUserByEmail(
            @RequestBody
            UserDto userdto
    ) {
        User user = userService.getUserByEmail(userdto.email());
        user.updateUser(userdto);

        return ResponseEntity.ok(new UserDto(user));
    }

    @DeleteMapping("/{email}")
    @Transactional
    public ResponseEntity deleteUserByEmail(
            @PathVariable
            String email
    ) {
        User user = userService.getUserByEmail(email);
        userRepository.delete(user);

        return ResponseEntity.noContent().build();
    }
}
