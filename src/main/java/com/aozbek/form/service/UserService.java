package com.aozbek.form.service;

import com.aozbek.form.model.User;
import com.aozbek.form.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) { this.userRepository = userRepository; }

    public boolean createUser(User user) {
        if (!(userRepository.existsByUsername(user.getUsername()))) {
            userRepository.save(user);
            return true;
        }
        else
            return false;
    }
}

