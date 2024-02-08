package com.aozbek.form.controller;

import com.aozbek.form.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<String> deleteUserByAdmin(@PathVariable String userId) {
        userService.deleteUserByAdmin(userId);
        return ResponseEntity.status(HttpStatus.OK).body("A user which has the id of " + userId
                + " has been deleted successfully.");
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteUserByUser() {
        userService.deleteUserByUser();
        return ResponseEntity.status(HttpStatus.OK).body("You have deleted your registered user successfully.");
    }
}
