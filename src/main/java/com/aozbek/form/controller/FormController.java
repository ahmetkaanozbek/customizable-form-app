package com.aozbek.form.controller;

import com.aozbek.form.dto.FormDto;
import com.aozbek.form.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/form")
public class FormController {
    private final FormService formService;

    @Autowired
    public FormController(FormService formService) { this.formService = formService; }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createForm(@RequestBody FormDto formDto) {
        formService.createForm(formDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "A new form has been created successfully."
        );
    }

    @PatchMapping(value = "/edit/{formId}")
    public ResponseEntity<String> editForm(@RequestBody FormDto formDto, @PathVariable String formId) {
        formService.editForm(formDto, formId);
        return ResponseEntity.status(HttpStatus.OK).body(
                "Form update has been made successfully."
        );
    }

    @DeleteMapping(value = "/delete/{formId}")
    public ResponseEntity<String> deleteFormAndFields(@PathVariable String formId) {
        formService.deleteFormAndFields(formId);
        return ResponseEntity.status(HttpStatus.OK).body(
                "Form has been deleted successfully."
        );
    }
}
