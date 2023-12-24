package com.aozbek.form.service;

import com.aozbek.form.enums.FieldTypes;
import com.aozbek.form.exceptions.FormNotFoundException;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;
    private final FormRepository formRepository;

    @Autowired
    public FieldService(FieldRepository fieldRepository, FormRepository formRepository) {
        this.fieldRepository = fieldRepository;
        this.formRepository = formRepository;
    }

    public void createFields(List<FormField> formFields) {
        fieldRepository.saveAll(formFields);
    }

    public boolean isValidFieldType(FormField formField) {
            Optional<Form> associatedForm = formRepository.getFormById(formField.getFormId());
            if (associatedForm.isPresent()) {
                // FieldTypes is the enum for all possible field types.
                String fieldType = formField.getFieldType().toUpperCase();
                return fieldType.equals(FieldTypes.TEXT.name()) || fieldType.equals(FieldTypes.NUMBER.name());
            }
            else
                throw new FormNotFoundException("There is no available form in database with this ID: " +
                        formField.getFormId());
    }
}
