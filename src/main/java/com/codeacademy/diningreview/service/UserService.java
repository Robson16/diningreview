package com.codeacademy.diningreview.service;

import com.codeacademy.diningreview.dto.UserResponse;
import com.codeacademy.diningreview.exception.DisplayNameAlreadyInUseException;
import com.codeacademy.diningreview.exception.UserNotFoundException;
import com.codeacademy.diningreview.model.User;
import com.codeacademy.diningreview.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = (List<User>) this.userRepository.findAll();
        return users.stream()
                .map(user -> new UserResponse(
                        user.getDisplayName(),
                        user.getCity(),
                        user.getState(),
                        user.getZipCode(),
                        user.getPeanutAllergyInterest(),
                        user.getEggAllergyInterest(),
                        user.getDairyAllergyInterest()
                ))
                .collect(Collectors.toList());
    }

    public UserResponse createUser(User user) {
        if (this.userRepository.existsByDisplayName(user.getDisplayName())) {
            throw new DisplayNameAlreadyInUseException("Nome de exibição já está em uso.");
        }
        User savedUser = this.userRepository.save(user);

        return new UserResponse(
                savedUser.getDisplayName(),
                savedUser.getCity(),
                savedUser.getState(),
                savedUser.getZipCode(),
                savedUser.getPeanutAllergyInterest(),
                savedUser.getEggAllergyInterest(),
                savedUser.getDairyAllergyInterest()
        );
    }

    public UserResponse updateUser(String displayName, User updatedUser) {
        Optional<User> existingUserOptional = this.userRepository.findByDisplayName(displayName);

        if (existingUserOptional.isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado.");
        }

        User existingUser = existingUserOptional.get();

        if (!existingUser.getDisplayName().equals(updatedUser.getDisplayName())) {
            throw new DisplayNameAlreadyInUseException("O nome de exibição não pode ser alterado.");
        }

        existingUser.setCity(updatedUser.getCity());
        existingUser.setState(updatedUser.getState());
        existingUser.setZipCode(updatedUser.getZipCode());
        existingUser.setPeanutAllergyInterest(updatedUser.getPeanutAllergyInterest());
        existingUser.setEggAllergyInterest(updatedUser.getEggAllergyInterest());
        existingUser.setDairyAllergyInterest(updatedUser.getDairyAllergyInterest());

        this.userRepository.save(existingUser);

        return new UserResponse(
                existingUser.getDisplayName(),
                existingUser.getCity(),
                existingUser.getState(),
                existingUser.getZipCode(),
                existingUser.getPeanutAllergyInterest(),
                existingUser.getEggAllergyInterest(),
                existingUser.getDairyAllergyInterest()
        );
    }

    public UserResponse getUserByDisplayName(String displayName) {
        Optional<User> existingUserOptional = this.userRepository.findByDisplayName(displayName);

        if (existingUserOptional.isEmpty()) {
            throw new UserNotFoundException("Usuário não encontrado.");
        }

        User existingUser = existingUserOptional.get();

        return new UserResponse(
                existingUser.getDisplayName(),
                existingUser.getCity(),
                existingUser.getState(),
                existingUser.getZipCode(),
                existingUser.getPeanutAllergyInterest(),
                existingUser.getEggAllergyInterest(),
                existingUser.getDairyAllergyInterest()
        );
    }

    public boolean userExists(String displayName) {
        return userRepository.existsByDisplayName(displayName);
    }
}
