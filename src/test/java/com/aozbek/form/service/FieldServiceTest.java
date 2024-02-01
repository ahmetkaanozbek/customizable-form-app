package com.aozbek.form.service;

import com.aozbek.form.exceptions.FieldTypeIsNotExpectedType;
import com.aozbek.form.exceptions.FieldTypeIsNotValidException;
import com.aozbek.form.exceptions.FormNotFoundException;
import com.aozbek.form.exceptions.UnauthorizedAccessException;
import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import com.aozbek.form.model.User;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.FormRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FieldServiceTest {

    @InjectMocks
    private FieldService underTestfieldService;
    @Mock
    private AuthService authService;
    @Mock
    private FieldRepository fieldRepository;
    @Mock
    private FormRepository formRepository;
    @Captor
    ArgumentCaptor<List<FormField>> formFieldsCaptor;

    @Test
    void createFields_checkIfFormExistWithThatIdAndFieldIsValidTypeAndSuccessfullyMadeRepositoryCallAndMadeByAuthorizedUser() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // when
        underTestfieldService.createFields(formFields, testForm.getId());

        // then
        assertEquals(testForm.getUserId(), testUser.getId());
        for (FormField formField : formFields) {
            assertTrue(underTestfieldService.isValidFieldType(formField));
        }
        verify(fieldRepository).saveAll(formFieldsCaptor.capture());
        assertEquals(formFields, formFieldsCaptor.getValue());
    }

    @Test
    void createFields_checkIfFormNotFoundExceptionIsThrownWhenThereIsNoAvailableFormWithThatFormId() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenThrow(FormNotFoundException.class);

        // when/then
        assertThrows(FormNotFoundException.class,
                () -> underTestfieldService.createFields(formFields, testForm.getId()));
        verify(fieldRepository, never()).saveAll(formFields);
    }

    @Test
    void createFields_checkIfUnauthorizedAccessExceptionIsThrownWhenAuthenticatedUserDoesntMatchWithUserAssociatedToForm() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        User unauthorizedTestUser = new User("5", "testUser", "testPassword");
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(unauthorizedTestUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // when/then
        // testUser's id doesn't match with the unauthorizedTestUser's id. So, exception will be thrown.
        assertThrows(UnauthorizedAccessException.class,
                () -> underTestfieldService.createFields(formFields, testForm.getId()));
        verify(fieldRepository, never()).saveAll(formFields);
    }

    @Test
    void createFields_checkIfFieldTypeIsNotValidExceptionIsThrownWhenOneOfTheFormFieldsHasInvalidFieldType() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField =  new FormField("3", "sample", "text", testForm.getId());
        FormField invalidTestFormField = new FormField("4", "sample", "invalidType", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);
        formFields.add(invalidTestFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // then
        assertThrows(FieldTypeIsNotValidException.class,
                () -> underTestfieldService.createFields(formFields, testForm.getId()));
        verify(fieldRepository, never()).saveAll(formFields);
    }
}