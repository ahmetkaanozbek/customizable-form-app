package com.aozbek.form.repository;

import com.aozbek.form.model.FormResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends MongoRepository<FormResponse, String> {
}
