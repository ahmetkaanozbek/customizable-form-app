package com.aozbek.form.repository;

import com.aozbek.form.model.Form;
import com.aozbek.form.model.FormField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
public class FieldRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    FieldRepository fieldRepository;

    @Test
    void findAllByFormId_checksIfItFindsAllFormFieldsByFormIdSuccessfully() {
        //given
        Form testForm = new Form("2", "test", "testForm", Instant.now(), "1");
        List<FormField> formFields = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FormField testFormField = new FormField(String.valueOf(i), "sample", "text", testForm.getId());
            formFields.add(testFormField);
            mongoTemplate.save(testFormField);
        }

        //when
        List<FormField> expectedFormFields = fieldRepository.findAllByFormId(testForm.getId());

        //then
        assertEquals(expectedFormFields.size(), formFields.size());
        assertTrue(expectedFormFields.containsAll(formFields));
        assertTrue(formFields.containsAll(expectedFormFields));
    }
}
