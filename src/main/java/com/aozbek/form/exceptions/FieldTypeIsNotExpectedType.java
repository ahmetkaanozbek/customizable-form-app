package com.aozbek.form.exceptions;

public class FieldTypeIsNotExpectedType extends RuntimeException {
    public FieldTypeIsNotExpectedType() {
        super("Field type doesn't match with the expected type.");
    }
}
