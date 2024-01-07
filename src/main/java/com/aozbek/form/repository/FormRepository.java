package com.aozbek.form.repository;

import com.aozbek.form.model.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormRepository extends MongoRepository<Form, String> {
    Optional<Form> getFormById(String id);
}
