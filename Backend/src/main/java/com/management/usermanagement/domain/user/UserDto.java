package com.management.usermanagement.domain.user;

public record UserDto(
        String email,
        String firstName,
        String lastName,
        Role role
) {
    public UserDto(User user) {
        this(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
