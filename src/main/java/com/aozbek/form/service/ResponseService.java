package com.aozbek.form.service;

import com.aozbek.form.model.FormResponse;
import com.aozbek.form.repository.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository responseRepository) {this.responseRepository = responseRepository; }

    public FormResponse saveResponse(FormResponse formResponse) {
        if (!(isValidType(formResponse)))
            throw new IllegalArgumentException("Required argument type is not matched with the filled argument type.");
        return responseRepository.save(formResponse);
    }

    /*
       isValidType method make validations. This program doesn't use validation annotations. Due to
       type of the ResponseValue field which is Object. Validation annotations don't work properly
       with all data types.
     */
    boolean isValidType(FormResponse formResponse) {
        if (formResponse.getFieldType().equals("short_answer"))
            return formResponse.getResponseValue() instanceof String &&
                    !((String) (formResponse.getResponseValue())).trim().isEmpty();
        else if (formResponse.getFieldType().equals("number"))
            return  formResponse.getResponseValue() instanceof Integer ||
                    formResponse.getResponseValue() instanceof Double;
        return false;
    }
}
