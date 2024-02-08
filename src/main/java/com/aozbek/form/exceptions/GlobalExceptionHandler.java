package com.aozbek.form.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<String> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
        log.warn("UsernameAlreadyExistsException exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FieldNotFoundException.class)
    public ResponseEntity<String> handleFieldNotFoundException(FieldNotFoundException ex) {
        log.warn("FieldNotFoundException exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FieldTypeIsNotExpectedType.class)
    public ResponseEntity<String> handleFieldTypeIsNotExpectedType(FieldTypeIsNotExpectedType ex) {
        log.warn("FieldTypeIsNotExpectedType exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FieldTypeIsNotValidException.class)
    public ResponseEntity<String> handleFieldTypeIsNotValidException(FieldTypeIsNotValidException ex) {
        log.warn("FieldTypeIsNotValidException exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(FormNotFoundException.class)
    public ResponseEntity<String> handleFormNotFoundException(FormNotFoundException ex) {
        log.warn("FormNotFoundException exception has been occurred. Message: " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<String> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        log.warn("UnauthorizedAccessException exception has been occurred. Message " + ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = "Error message: " + error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        log.warn("MethodArgumentNotValidException exception has been occurred. Message: " + errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
