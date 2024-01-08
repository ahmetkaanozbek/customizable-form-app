package com.aozbek.form.service;

import com.aozbek.form.exceptions.FieldNotFoundException;
import com.aozbek.form.exceptions.FieldTypeIsNotExpectedType;
import com.aozbek.form.model.FormField;
import com.aozbek.form.model.FormResponse;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final FieldRepository fieldRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository, FieldRepository fieldRepository) {
        this.responseRepository = responseRepository;
        this.fieldRepository = fieldRepository;
    }

    public void createResponse(List<FormResponse> formResponses) {
        for (FormResponse formResponse : formResponses) {
            String fieldId = formResponse.getFormFieldId();
            FormField associatedField = fieldRepository.getFormFieldById(fieldId)
                    .orElseThrow(() -> new FieldNotFoundException("This field id doesn't match with an " +
                            "existing one: " + formResponse.getFormFieldId()));
            if (!(isValidResponseType(formResponse, associatedField))) {
                throw new FieldTypeIsNotExpectedType();
            }
        }
        responseRepository.saveAll(formResponses);
    }

    /*
        isValidType method make validations. This program doesn't use validation annotations. Due to
        type of the ResponseValue field which is Object. Validation annotations don't work properly
        with all data types.
    */
    public boolean isValidResponseType(FormResponse formResponse, FormField associatedField) {
        String expectedType = associatedField.getFieldType();
        Object responseValue = formResponse.getResponseValue();

        if (expectedType.equals("text")) {
            return responseValue instanceof String &&
                    !((String) responseValue).trim().isEmpty();
        }
        else if (expectedType.equals("number")) {
            return responseValue instanceof Integer ||
                    responseValue instanceof Double;
        }
        return false;
    }
}
