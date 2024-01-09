package com.aozbek.form.controller;

import com.aozbek.form.model.FormField;
import com.aozbek.form.service.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/form/fields")
public class FieldController {
    final private FieldService fieldService;

    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    @PostMapping(value = "/edit/{formId}")
    public ResponseEntity<String> createFields(@RequestBody List<FormField> formFields,
                                               @PathVariable String formId) {
        fieldService.createFields(formFields, formId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Fields have been created successfully.");
    }

    @PatchMapping(value = "/edit/{formId}")
    public ResponseEntity<String> updateFields(@RequestBody List<FormField> formFields,
                                               @PathVariable String formId) {
        fieldService.updateFields(formFields, formId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Fields update has been made successfully.");
    }

    @DeleteMapping(value = "/edit/{formId}")
    public ResponseEntity<String> deleteField(@RequestBody FormField formField,
                                              @PathVariable String formId) {
        fieldService.deleteField(formField, formId);
        return ResponseEntity.status(HttpStatus.OK)
                .body("A field has been deleted successfully.");
    }

    @GetMapping(value = "/get/{formId}")
    public ResponseEntity<List<FormField>> getFieldsAndForm(@PathVariable String formId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(fieldService.getFieldsAndForm(formId));
    }
}