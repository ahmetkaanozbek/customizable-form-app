package com.aozbek.form.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException() {
        super("This username is not available. Please choose another one.");
    }
}
