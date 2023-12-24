package com.aozbek.form.controller;

import com.aozbek.form.model.Form;
import com.aozbek.form.service.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/form")
public class FormController {
    private final FormService formService;

    @Autowired
    public FormController(FormService formService) { this.formService = formService; }

    @PostMapping("/new")
    public Form createForm(@RequestBody Form form) {
        return formService.createForm(form);
    }

}
