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

    @PostMapping("/create")
    public ResponseEntity<String> createForm(@RequestBody FormDto formDto) {
        formService.createForm(formDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                "A new form has been created successfully."
        );
    }
}
