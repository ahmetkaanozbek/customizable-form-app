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

@RestController
@RequestMapping(value = "/form/responses")
public class ResponseController {
    final private ResponseService responseService;

    @Autowired
    ResponseController(ResponseService responseService) {this.responseService = responseService; }

    @PostMapping(value = "/fill")
    ResponseEntity<String> createResponse(@RequestBody FormResponse formResponse) {
        try {
            responseService.saveResponse(formResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body("Field has been created successfully.");
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to create field. " + iae.getMessage());
        }
    }
}
