package com.aozbek.form.service;

import com.aozbek.form.model.User;
import com.aozbek.form.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserByAdmin(String userId) {
        userRepository.deleteById(userId);
    }

    public void deleteUserByUser() {
        User currentUser = authService.getCurrentUser();
        userRepository.deleteById(currentUser.getId());
    }
}

