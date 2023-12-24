package com.aozbek.form.exceptions;

public class FieldNotFoundException extends RuntimeException{
    public FieldNotFoundException(String message) {
        super(message);
    }
}
