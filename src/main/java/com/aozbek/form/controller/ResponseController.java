package com.aozbek.form.controller;

import com.aozbek.form.model.FormResponse;
import com.aozbek.form.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/form/response")
public class ResponseController {
    final private ResponseService responseService;

    @Autowired
    ResponseController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<String> createResponse(@RequestBody List<FormResponse> formResponses) {
        responseService.createResponse(formResponses);
        return ResponseEntity.status(HttpStatus.CREATED).body("Form responses has been saved successfully.");
    }
}
