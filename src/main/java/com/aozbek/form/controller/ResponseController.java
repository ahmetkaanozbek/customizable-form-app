package com.aozbek.form.controller;

import com.aozbek.form.exceptions.FieldNotFoundException;
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

    @Autowired
    ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping(value = "/submit")
    public ResponseEntity<String> createResponse(@RequestBody List<FormResponse> formResponses) {
        for (FormResponse formResponse : formResponses) {
            try {
                if (!(responseService.isValidResponseType(formResponse)))
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Field type doesn't match with the " +
                            "expected type.");
            } catch (FieldNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This field id doesn't match with an " +
                        "existing one: " + formResponse.getFormFieldId());
            }
        }
        responseService.saveResponse(formResponses);
        return ResponseEntity.status(HttpStatus.CREATED).body("Form responses saved successfully.");
    }
}
