package com.aozbek.form.service;

import com.aozbek.form.enums.FieldTypes;
import com.aozbek.form.exceptions.FieldNotFoundException;
import com.aozbek.form.exceptions.FieldTypeIsNotValidException;
import com.aozbek.form.exceptions.FormNotFoundException;
import com.aozbek.form.exceptions.UnauthorizedAccessException;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldService {
    private final FieldRepository fieldRepository;
    private final FormRepository formRepository;
    private final AuthService authService;

    @Autowired
    public FieldService(FieldRepository fieldRepository,
                        FormRepository formRepository,
                        AuthService authService) {
        this.fieldRepository = fieldRepository;
        this.formRepository = formRepository;
        this.authService = authService;
    }

    public void createFields(List<FormField> formFields, String formId) {
        String userId = authService.getCurrentUser().getId();
        Form form = formRepository.getFormById(formId)
                .orElseThrow(() -> new FormNotFoundException(
                        "There is no available form in database with this ID: " + formId));
        if (!(form.getUserId().equals(userId)))
            throw new UnauthorizedAccessException();
        for (FormField formField : formFields) {
            if (!(isValidFieldType(formField))) {
                throw new FieldTypeIsNotValidException();
            }
            formField.setFormId(formId);
        }
        fieldRepository.saveAll(formFields);
    }

    public void updateFields(List<FormField> formFields, String formId) {
        String userId = authService.getCurrentUser().getId();
        Form form = formRepository.getFormById(formId)
                .orElseThrow(() -> new FormNotFoundException(
                        "There is no available form in database with this ID: " + formId));
        if (!(form.getUserId().equals(userId)))
            throw new UnauthorizedAccessException();
        for (FormField formField : formFields) {
            FormField currentFormField = fieldRepository.getFormFieldById(formField.getId())
                    .orElseThrow(() -> new FieldNotFoundException(
                            "There is no available field in database with this ID: " + formField.getId()));
            if (!(isValidFieldType(formField))) {
                throw new FieldTypeIsNotValidException();
            }
            currentFormField.setFieldType(formField.getFieldType());
            currentFormField.setFieldLabel(formField.getFieldLabel());
        }
    }

    public void deleteField(FormField formField, String formId) {
        String userId = authService.getCurrentUser().getId();
        Form form = formRepository.getFormById(formId)
                .orElseThrow(() -> new FormNotFoundException(
                        "There is no available form in database with this ID: " + formId));
        if (!(form.getUserId().equals(userId)))
            throw new UnauthorizedAccessException();
        FormField currentFormField = fieldRepository.getFormFieldById(formField.getId())
                .orElseThrow(() -> new FieldNotFoundException(
                        "There is no available field in database with this ID: " + formField.getId()));
        fieldRepository.delete(currentFormField);
    }

    public boolean isValidFieldType(FormField formField) {
        // FieldTypes is the enum for all possible field types.
        String fieldType = formField.getFieldType().toUpperCase();
        return fieldType.equals(FieldTypes.TEXT.name()) ||
                fieldType.equals(FieldTypes.NUMBER.name());
    }
}
