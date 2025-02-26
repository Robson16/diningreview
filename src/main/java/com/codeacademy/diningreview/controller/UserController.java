package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.ErrorMessage;
import com.codeacademy.diningreview.dto.UserResponse;
import com.codeacademy.diningreview.exception.DisplayNameAlreadyInUseException;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = this.userService.gelAllUsers();

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            UserResponse response = userService.createUser(user);

            return ResponseEntity.ok(response);
        } catch (DisplayNameAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(new ErrorMessage(e.getMessage()));
        }
    }
}
