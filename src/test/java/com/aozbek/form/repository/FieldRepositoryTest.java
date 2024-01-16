package com.aozbek.form.repository;

import com.aozbek.form.model.FormField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataMongoTest
@Testcontainers
@ContextConfiguration(classes = MongoDBTestContainerConfig.class)
public class FieldRepositoryTest {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    FieldRepository underTest;

    @Test
    void itShouldGetFormFieldById() {

        //given
        FormField formField = new FormField(
                "5",
                "School",
                "text",
                "2"
        );
        mongoTemplate.save(formField);

        //when
        Optional<FormField> searchedFormField = underTest.getFormFieldById("5");

        //then
        assertThat(searchedFormField).isPresent();
    }
}
