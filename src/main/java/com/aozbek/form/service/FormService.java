package com.aozbek.form.service;

import com.aozbek.form.dto.FormDto;
import com.aozbek.form.mapper.FormMapper;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.User;
import com.aozbek.form.repository.FormRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final AuthService authService;
    private final FormMapper formMapper;

    public FormService(FormRepository formRepository,
                       AuthService authService,
                       FormMapper formMapper) {
        this.formRepository = formRepository;
        this.authService = authService;
        this.formMapper = formMapper;
    }

    public void createForm(FormDto formDto) {
        User user = authService.getCurrentUser();
        String userId = user.getId();
        Form form = formMapper.map(formDto, userId);
        formRepository.save(form);
    }

    public void editForm(FormDto formDto, String formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new NoSuchElementException("No form exist with this id."));
        form.setFormName(formDto.getFormName());
        form.setDescription(formDto.getDescription());
        formRepository.save(form);
    }
}
