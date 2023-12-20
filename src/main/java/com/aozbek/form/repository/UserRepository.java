package com.aozbek.form.repository;

import com.aozbek.form.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> getByUsername(String username);
    boolean existsByUsername(String username);
}
