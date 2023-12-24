package com.aozbek.form.exceptions;

public class FormNotFoundException extends RuntimeException{
    public FormNotFoundException(String message) {
        super(message);
    }
}
