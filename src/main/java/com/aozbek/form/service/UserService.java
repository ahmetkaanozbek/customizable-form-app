package com.aozbek.form.service;

import com.aozbek.form.exceptions.UnauthorizedAccessException;
import com.aozbek.form.model.User;
import com.aozbek.form.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public UserService(UserRepository userRepository,
                       AuthService authService) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    public void deleteUser(String userId) {
        User currentUser = authService.getCurrentUser();
        if (!(currentUser.getId().equals(userId))) {
            throw new UnauthorizedAccessException();
        }
        userRepository.deleteById(userId);
    }
}

