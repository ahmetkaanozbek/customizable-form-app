package com.aozbek.form.service;

import com.aozbek.form.model.Form;
import com.aozbek.form.repository.FormRepository;
import org.springframework.stereotype.Service;

@Service
public class FormService {
    private final FormRepository formRepository;

    public FormService(FormRepository formRepository) { this.formRepository = formRepository; }

    public Form createForm(Form form) {
        return formRepository.save(form);
    }
}
