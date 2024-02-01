package com.aozbek.form.service;

import com.aozbek.form.exceptions.FieldNotFoundException;
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

    @Test
    void updateFields_checkIfFieldUpdateSuccessfullyDoneByAuthorizedUserAndWithAnExistingFormAndFormFieldAndWithAValidFieldType() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        List<FormField> formFields = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FormField formField = new FormField(String.valueOf(i), "test", "text", testForm.getId());
            formFields.add(formField);
        }

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));
        for (FormField formField : formFields) {
            when(fieldRepository.getFormFieldById(formField.getId()))
                    .thenReturn(Optional.of(formField));
        }

        // when
        underTestfieldService.updateFields(formFields, testForm.getId());

        // then
        for (FormField formField : formFields) {
            FormField updatedFormField = fieldRepository.getFormFieldById(formField.getId()).orElse(null);
            assertNotNull(updatedFormField);
            assertEquals(formField.getFieldType(), updatedFormField.getFieldType());
            assertEquals(formField.getFieldLabel(), updatedFormField.getFieldLabel());
        }
    }

    @Test
    void updateFields_checkIfFormNotFoundExceptionIsThrownWhenThereIsNoAvailableFormWithThatFormId() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField = mock(FormField.class);
        testFormField.setId("3");
        testFormField.setFieldLabel("sample");
        testFormField.setFieldType("text");
        testFormField.setFormId(testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(testFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenThrow(FormNotFoundException.class);

        // when/then
        assertThrows(FormNotFoundException.class,
                () -> underTestfieldService.updateFields(formFields, testForm.getId()));
        verify(testFormField, never()).setFieldType(testFormField.getFieldType());
        verify(testFormField, never()).setFieldLabel(testFormField.getFieldLabel());
    }

    @Test
    void updateFields_checkIfUnauthorizedAccessExceptionIsThrownWhenAuthenticatedUserDoesntMatchWithUserAssociatedToForm() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        User unauthorizedTestUser = new User("5", "testUser", "testPassword");
        FormField testFormField = mock(FormField.class);
        testFormField.setId("3");
        testFormField.setFieldLabel("sample");
        testFormField.setFieldType("text");
        testFormField.setFormId(testForm.getId());
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
                () -> underTestfieldService.updateFields(formFields, testForm.getId()));
        verify(testFormField, never()).setFieldType(testFormField.getFieldType());
        verify(testFormField, never()).setFieldLabel(testFormField.getFieldLabel());
    }

    @Test
    void updateFields_checkIfFieldNotFoundExceptionIsThrownWhenOneOfTheFormFieldsHasNotFoundInDatabase() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField notExistTestFormField = mock(FormField.class);
        notExistTestFormField.setId("3");
        notExistTestFormField.setFieldLabel("sample");
        notExistTestFormField.setFieldType("text");
        notExistTestFormField.setFormId(testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(notExistTestFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));
        when(fieldRepository.getFormFieldById(notExistTestFormField.getId()))
                .thenThrow(FieldNotFoundException.class);

        // when/then
        assertThrows(FieldNotFoundException.class,
                () -> underTestfieldService.updateFields(formFields, testForm.getId()));
        verify(notExistTestFormField, never()).setFieldType(notExistTestFormField.getFieldType());
        verify(notExistTestFormField, never()).setFieldLabel(notExistTestFormField.getFieldLabel());
    }

    @Test
    void updateFields_checkIfFieldTypeIsNotValidExceptionIsThrownWhenOneOfTheFormFieldsHasInvalidFieldType() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField invalidTypeTestFormField = new FormField("3", "sample", "invalidType", testForm.getId());
        List<FormField> formFields = new ArrayList<>();
        formFields.add(invalidTypeTestFormField);

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));
        when(fieldRepository.getFormFieldById(invalidTypeTestFormField.getId()))
                .thenReturn(Optional.of(invalidTypeTestFormField));

        // when/then
        for (FormField formField : formFields) {
            assertFalse(underTestfieldService.isValidFieldType(formField));
        }
        assertThrows(FieldTypeIsNotValidException.class,
                () -> underTestfieldService.updateFields(formFields, testForm.getId()));
    }

    @Test
    void deleteField_checkIfFieldDeletionSuccessfullyMadeByAuthorizedUserAndMadeForExistingFormAndField() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField = new FormField("3", "sample", "number", testForm.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));
        when(fieldRepository.getFormFieldById(testFormField.getId()))
                .thenReturn(Optional.of(testFormField));

        // when
        underTestfieldService.deleteField(testFormField, testForm.getId());

        // then
        verify(fieldRepository).delete(testFormField);
    }

    @Test
    void deleteField_checkIfFormNotFoundExceptionIsThrownWhenNoFormIsFoundWithTheGivenId() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField = new FormField("3", "sample", "number", testForm.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenThrow(FormNotFoundException.class);

        // when/then
        assertThrows(FormNotFoundException.class,
                () -> underTestfieldService.deleteField(testFormField, testForm.getId()));
        verify(fieldRepository, never()).delete(testFormField);
    }

    @Test
    void deleteField_checkIfUnauthorizedAccessExceptionIsThrownWhenAUserDoesntHavePermissionToDeleteThatField() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        User unauthorizedUser = new User("4", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField = new FormField("3", "sample", "number", testForm.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(unauthorizedUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // when/then
        assertNotEquals(testUser.getId(), unauthorizedUser.getId());
        assertThrows(UnauthorizedAccessException.class,
                () -> underTestfieldService.deleteField(testFormField, testForm.getId()));
        verify(fieldRepository, never()).delete(testFormField);
    }

    @Test
    void deleteField_checkIfFieldNotFoundExceptionIsThrownWhenNoExistingFormFieldWithTheGivenId() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());
        FormField testFormField = new FormField("3", "sample", "number", testForm.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));
        when(fieldRepository.getFormFieldById(testFormField.getId()))
                .thenThrow(FieldNotFoundException.class);

        // when/then
        assertThrows(FieldNotFoundException.class,
                () -> underTestfieldService.deleteField(testFormField, testForm.getId()));
        verify(fieldRepository, never()).delete(testFormField);
    }

    @Test
    void getFieldsAndForm_checkIfItCanGetFormAndFieldsBelongsToThatFormSuccessfully() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // when
        underTestfieldService.getFieldsAndForm(testForm.getId());

        // then
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(fieldRepository).findAllByFormId(argumentCaptor.capture());
        assertEquals(testForm.getId(), argumentCaptor.getValue());
    }

    @Test
    void getFieldsAndForm_checkIfFormNotFoundExceptionIsThrownWhenThereIsNoExistingFormInDatabaseWithTheGivenId() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(testUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenThrow(FormNotFoundException.class);

        // when/then
        assertThrows(FormNotFoundException.class,
                () -> underTestfieldService.getFieldsAndForm(testForm.getId()));
        verify(fieldRepository, never()).getFormFieldById(testForm.getId());
    }
    @Test
    void getFieldsAndForm_checkIfUnauthorizedAccessExceptionIsThrownWhenAnAuthorizedUserTryToGetFormAndFields() {
        // given
        User testUser = new User("1", "testUser", "testPassword");
        User unauthorizedTestUser = new User("3", "testUser", "testPassword");
        Form testForm = new Form("2", "testFormName", "testFormDescription", Instant.now(), testUser.getId());

        // mockBehavior
        when(authService.getCurrentUser())
                .thenReturn(unauthorizedTestUser);
        when(formRepository.getFormById(testForm.getId()))
                .thenReturn(Optional.of(testForm));

        // when/then
        assertThrows(UnauthorizedAccessException.class,
                () -> underTestfieldService.getFieldsAndForm(testForm.getId()));
        verify(fieldRepository, never()).getFormFieldById(testForm.getId());
    }
}