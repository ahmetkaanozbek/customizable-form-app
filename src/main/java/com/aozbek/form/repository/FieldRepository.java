package com.aozbek.form.repository;

import com.aozbek.form.model.FormField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FieldRepository extends MongoRepository<FormField, String> {
    Optional<FormField> getFormFieldById(String id);
}
