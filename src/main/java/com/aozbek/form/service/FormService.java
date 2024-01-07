package com.aozbek.form.service;

import com.aozbek.form.model.Form;
import com.aozbek.form.model.User;
import com.aozbek.form.repository.FormRepository;
import com.aozbek.form.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FormService {
    private final FormRepository formRepository;
    private final UserRepository userRepository;

    public FormService(FormRepository formRepository, UserRepository userRepository) {
        this.formRepository = formRepository;
        this.userRepository = userRepository;
    }

    public void createForm(Form form) {
        String username = SecurityContextHolder
                .getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found for that username"));
        String userId = user.getId();
        System.out.println("User id of the user is: " + userId);
        form.setUserId(userId);
        formRepository.save(form);
    }
}
