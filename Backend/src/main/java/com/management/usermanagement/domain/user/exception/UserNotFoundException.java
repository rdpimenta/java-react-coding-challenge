package com.management.usermanagement.domain.user.exception;


import jakarta.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException() {
        super("User not found with email provided.");
    }
}
