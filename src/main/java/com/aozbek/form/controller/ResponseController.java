package com.aozbek.form.controller;

import com.aozbek.form.model.FormField;
import com.aozbek.form.model.FormResponse;
import com.aozbek.form.repository.FieldRepository;
import com.aozbek.form.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/form/responses")
public class ResponseController {
    final private ResponseService responseService;
    final private FieldRepository fieldRepository;

    @Autowired
    ResponseController(ResponseService responseService, FieldRepository fieldRepository) {
        this.responseService = responseService;
        this.fieldRepository = fieldRepository;
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<String> createResponse(@RequestBody List<FormResponse> formResponses) {
        for (FormResponse formResponse : formResponses) {
            String fieldId = formResponse.getFormFieldId();
            Optional<FormField> associatedField = fieldRepository.getFormFieldById(fieldId);

            if (associatedField.isPresent()) {
                String expectedType = associatedField.get().getFieldType();
                Object responseValue = formResponse.getResponseValue();

                if (!(isValidType(expectedType, responseValue)))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field type doesn't match with the " +
                            "expected type.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This field id doesn't match with an " +
                        "existing one: " + fieldId);
            }
        }
        responseService.saveResponse(formResponses);
        return ResponseEntity.status(HttpStatus.CREATED).body("Form responses saved successfully.");
    }

    /*
       isValidType method make validations. This program doesn't use validation annotations. Due to
       type of the ResponseValue field which is Object. Validation annotations don't work properly
       with all data types.
     */
    private boolean isValidType(String expectedType, Object responseValue) {
        if (expectedType.equals("text"))
            return responseValue instanceof String &&
                    !((String) responseValue).trim().isEmpty();
        else if (expectedType.equals("number"))
            return  responseValue instanceof Integer ||
                    responseValue instanceof Double;
        return false;
    }

}
