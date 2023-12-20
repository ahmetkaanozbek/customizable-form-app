package com.aozbek.form.controller;

import com.aozbek.form.model.User;
import com.aozbek.form.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {
    UserService userService;
    @Autowired
    UserController(UserService userService) { this.userService = userService; }
    @PostMapping(value = "/register")
    ResponseEntity<String> createUser(@RequestBody @Valid User user) {

        if (userService.createUser(user))
            return ResponseEntity.status(HttpStatus.CREATED).body("Your registration has been successful.");
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists.");
        }
    }
}
