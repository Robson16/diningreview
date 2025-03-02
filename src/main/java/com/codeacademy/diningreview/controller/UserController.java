package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.UserResponse;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.service.UserService;
import com.codeacademy.diningreview.util.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        List<UserResponse> response = this.userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{displayName}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByDisplayName(@PathVariable String displayName) {
        UserResponse response = this.userService.getUserByDisplayName(displayName);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody User user) {
        UserResponse response = this.userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.success("Usuário criado com sucesso!", response));
    }

    @PutMapping("/{displayName}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String displayName, @RequestBody User user) {
        UserResponse response = this.userService.updateUser(displayName, user);
        return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso!", response));
    }
}
