package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.UserResponse;
import com.codeacademy.diningreview.exception.DisplayNameAlreadyInUseException;
import com.codeacademy.diningreview.exception.UserNotFoundException;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.service.UserService;
import com.codeacademy.diningreview.util.ApiResponse;
import org.springframework.http.HttpStatus;
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
        List<UserResponse> response = userService.getAllUsers();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{displayName}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserByDisplayName(@PathVariable String displayName) {
        try {
            UserResponse response = userService.getUserByDisplayName(displayName);

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Usuário não encontrado"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody User user) {
        try {
            UserResponse response = userService.createUser(user);

            return ResponseEntity.ok(ApiResponse.success("Usuário criado com sucesso!", response));
        } catch (DisplayNameAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{displayName}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable String displayName, @RequestBody User user) {
        try {
            UserResponse response = userService.updateUser(displayName, user);

            return ResponseEntity.ok(ApiResponse.success("Usuário atualizado com sucesso!", response));
        } catch (DisplayNameAlreadyInUseException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Usuário não encontrado"));
        }
    }
}
