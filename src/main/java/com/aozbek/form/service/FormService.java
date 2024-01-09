package com.aozbek.form.service;

import com.aozbek.form.dto.FormDto;
import com.aozbek.form.exceptions.UnauthorizedAccessException;
import com.aozbek.form.mapper.FormMapper;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.model.User;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.FormRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final AuthService authService;
    private final FormMapper formMapper;
    private final FieldRepository fieldRepository;

    public FormService(FormRepository formRepository,
                       AuthService authService,
                       FormMapper formMapper,
                       FieldRepository fieldRepository) {
        this.formRepository = formRepository;
        this.authService = authService;
        this.formMapper = formMapper;
        this.fieldRepository = fieldRepository;
    }

    public void createForm(FormDto formDto) {
        if (formDto.getFormName() == null || formDto.getFormName().trim().isEmpty())
            formDto.setFormName("Untitled");
        User user = authService.getCurrentUser();
        String userId = user.getId();
        Form form = formMapper.map(formDto, userId);
        formRepository.save(form);
    }

    public void editForm(FormDto formDto, String formId) {
        Form form = formRepository.getFormById(formId)
                .orElseThrow(() -> new NoSuchElementException("No form exist with this id."));
        String userId = authService.getCurrentUser().getId();
        if (!(form.getUserId().equals(userId))) {
            throw new UnauthorizedAccessException();
        }
        form.setFormName(formDto.getFormName());
        form.setDescription(formDto.getDescription());
        formRepository.save(form);
    }

    @Transactional
    public void deleteFormAndFields(String formId) {
        Form form = formRepository.getFormById(formId)
                .orElseThrow(() -> new NoSuchElementException("No form exist with this id."));
        String userId = authService.getCurrentUser().getId();
        if (!(form.getUserId().equals(userId))) {
            throw new UnauthorizedAccessException();
        }
        List<FormField> formFields = fieldRepository.findAllByFormId(formId);
        formRepository.delete(form);
        fieldRepository.deleteAll(formFields);
    }
}
