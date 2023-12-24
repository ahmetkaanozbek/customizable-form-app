package com.aozbek.form.controller;

import com.aozbek.form.enums.FieldTypes;
import com.aozbek.form.exceptions.FormNotFoundException;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.FormRepository;
import com.aozbek.form.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/form/fields")
public class FieldController {
    final private FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping(value = "/new")
    public ResponseEntity<String> createFields(@RequestBody List<FormField> formFields) {
        for (FormField formField : formFields) {
            try {
                if (!(fieldService.isValidFieldType(formField)))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field types should match with the " +
                            "determined types defined in the FieldTypes enum.");
            } catch (FormNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no available form in database " +
                        "with this ID: "  + formField.getFormId());
            }
        }
        fieldService.createFields(formFields);
        return ResponseEntity.status(HttpStatus.CREATED).body("Fields have been created successfully.");
    }
}