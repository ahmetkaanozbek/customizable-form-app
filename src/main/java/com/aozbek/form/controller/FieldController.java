package com.aozbek.form.controller;

import com.aozbek.form.model.FormField;
import com.aozbek.form.repository.FieldRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/form/fields")
public class FieldController {
    final private FieldRepository fieldRepository;

    FieldController(FieldRepository fieldRepository) { this.fieldRepository = fieldRepository; }

    @PostMapping(value = "/new")
    public FormField createField(@RequestBody FormField formField) {
        return fieldRepository.save(formField);
    }// When I add users I also need to check if the request was sent by an existing user.
}
