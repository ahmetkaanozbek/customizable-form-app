package com.aozbek.form.repository;

import com.aozbek.form.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User getByUsername(String username);
}
