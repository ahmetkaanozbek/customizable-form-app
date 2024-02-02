package com.aozbek.form.controller;

import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.model.User;
import com.aozbek.form.service.FieldService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = FieldController.class)
@ExtendWith(MockitoExtension.class)
class FieldControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FieldService fieldService;

    @Test
    void createFields_checkIfFieldServiceCreateFieldsMethodCalledCorrectlyAndResponseToRequestIsTheExpectedOne() throws Exception {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // when
        mockMvc.perform(post("/api/form/fields/edit/{formId}", testForm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formFields)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Fields have been created successfully."));

        // then
        verify(fieldService, times(1)).createFields(formFields, testForm.getId());
    }

    @Test
    void updateFields_checkIfFieldServiceUpdateFieldsMethodCalledCorrectlyAndResponseToRequestIsTheExpectedOne() throws Exception {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // when
        mockMvc.perform(patch("/api/form/fields/edit/{formId}", testForm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(formFields)))
                .andExpect(status().isOk())
                .andExpect(content().string("Fields update has been made successfully."));

        // then
        verify(fieldService, times(1)).updateFields(formFields, testForm.getId());
    }

    @Test
    void deleteField_checkIfFieldServiceDeleteFieldMethodCalledCorrectlyAndResponseToRequestIsTheExpectedOne() throws Exception {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());

        // when
        mockMvc.perform(delete("/api/form/fields/edit/{formId}", testForm.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testFormField)))
                .andExpect(status().isOk())
                .andExpect(content().string("A field has been deleted successfully."));

        // then
        verify(fieldService, times(1)).deleteField(testFormField, testForm.getId());
    }

    @Test
    void getFieldsAndForm_checkIfFieldServiceGetFieldsAndFormMethodCalledCorrectlyAndResponseToRequestIsTheExpectedOne() throws Exception {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // mockBehavior
        when(fieldService.getFieldsAndForm(testForm.getId()))
                .thenReturn(formFields);

        // when
        mockMvc.perform(get("/api/form/fields/get/{formId}", testForm.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(formFields)));

        // then
        verify(fieldService, times(1)).getFieldsAndForm(testForm.getId());
    }
}