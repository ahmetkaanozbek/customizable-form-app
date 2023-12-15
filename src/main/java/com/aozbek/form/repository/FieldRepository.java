package com.aozbek.form.repository;

import com.aozbek.form.model.FormField;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FieldRepository extends MongoRepository<FormField, String> {
}
