package com.aozbek.form.service;

import com.aozbek.form.model.FormResponse;
import com.aozbek.form.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {this.responseRepository = responseRepository; }

    public List<FormResponse> saveResponse(List<FormResponse> formResponses) {
        return responseRepository.saveAll(formResponses);
    }
}
