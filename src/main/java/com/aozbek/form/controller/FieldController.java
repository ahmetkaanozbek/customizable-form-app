package com.aozbek.form.controller;

import com.aozbek.form.model.FormField;
import com.aozbek.form.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/form/fields")
public class FieldController {
    final private FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createFields(@RequestBody List<FormField> formFields) {
        fieldService.createFields(formFields);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Fields have been created successfully.");
    }
}