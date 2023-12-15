package com.aozbek.form.controller;

import com.aozbek.form.model.Form;
import com.aozbek.form.repository.FormRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/form")
public class FormController {
    private final FormRepository formRepository;

    FormController(FormRepository formRepository) { this.formRepository = formRepository; }

    @PostMapping("/new")
    public Form createForm(@RequestBody Form form) {
        return formRepository.save(form);
    }

}
