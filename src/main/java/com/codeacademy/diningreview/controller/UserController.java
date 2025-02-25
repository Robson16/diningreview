package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.CreateUserResponse;
import com.codeacademy.diningreview.exception.DisplayNameAlreadyInUseException;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            CreateUserResponse response = userService.createUser(user);

            return ResponseEntity.ok(response);
        } catch (DisplayNameAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(new CreateUserResponse(null, user.getDisplayName(), e.getMessage()));
        }
    }
}
