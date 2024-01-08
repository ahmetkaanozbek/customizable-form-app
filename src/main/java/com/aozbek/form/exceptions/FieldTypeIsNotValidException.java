package com.aozbek.form.exceptions;

public class FieldTypeIsNotValidException extends RuntimeException {
    public FieldTypeIsNotValidException() {
        super("Field types should match with the determined types defined in the FieldTypes enum.");
    }
}
