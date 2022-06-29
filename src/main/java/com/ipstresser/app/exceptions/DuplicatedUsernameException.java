package com.ipstresser.app.exceptions;

public class DuplicatedUsernameException extends RuntimeException {

    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
