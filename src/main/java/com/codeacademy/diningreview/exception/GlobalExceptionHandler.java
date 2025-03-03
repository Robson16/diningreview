package com.codeacademy.diningreview.exception;

import com.codeacademy.diningreview.util.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Catch @Valid validation errors in DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(ApiResponse.error("Erro de validação", errors));
    }

    // Catches ConstraintViolationException errors (e.g. validations on @PathVariable or @RequestParam)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<String>> handleConstraintViolationException(ConstraintViolationException e) {
        return ResponseEntity.badRequest().body(ApiResponse.error("Erro de validação", e.getMessage()));
    }

    // Catch enums MethodArgumentTypeMismatchException errors (e.g. validation of enums on @PathVariable or @RequestParam)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Class<?> requiredType = ex.getRequiredType();
        if (requiredType != null && requiredType.isEnum()) {
            String allowedValues = Arrays.toString(requiredType.getEnumConstants());
            String errorMessage = "Valor inválido para " + requiredType.getSimpleName() +
                    ". Os valores permitidos são: " + allowedValues;
            return ResponseEntity.badRequest().body(ApiResponse.error(errorMessage));
        }
        return ResponseEntity.badRequest().body(ApiResponse.error("Valor inválido: " + ex.getValue()));
    }

    // Catches exceptions from NotFoundException
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
    }

    // Catches exceptions from ForbiddenActionException
    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<ApiResponse<String>> handleForbiddenActionException(ForbiddenActionException e) {
        return ResponseEntity.status(403).body(ApiResponse.error(e.getMessage()));
    }

    // Catches exceptions from RestaurantAlreadyExistsException
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse<String>> handleRestaurantAlreadyExistsException(AlreadyExistsException e) {
        return ResponseEntity.status(409).body(ApiResponse.error(e.getMessage()));
    }

    // Catches general and unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error("Erro interno.", e.getMessage()));
    }
}
