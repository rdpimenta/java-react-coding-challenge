package com.management.usermanagement.domain.user.exception;

public class DuplicatedUserException extends RuntimeException {
    public DuplicatedUserException() {
        super("Email is already attached to another user.");
    }
}
