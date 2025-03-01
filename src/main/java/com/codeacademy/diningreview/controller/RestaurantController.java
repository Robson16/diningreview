package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.RestaurantResponse;
import com.codeacademy.diningreview.exception.NotFoundException;
import com.codeacademy.diningreview.exception.RestaurantAlreadyExistsException;
import com.codeacademy.diningreview.model.Restaurant;
import com.codeacademy.diningreview.service.RestaurantService;
import com.codeacademy.diningreview.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getAllRestaurants() {
        List<RestaurantResponse> response = restaurantService.getAllRestaurants();

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurantDetails(@PathVariable Long id) {
        try {
            RestaurantResponse response = restaurantService.getRestaurantById(id);

            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getRestaurantsByZipAndAllergy(
            @RequestParam String zipCode,
            @RequestParam(defaultValue = "false") boolean peanut,
            @RequestParam(defaultValue = "false") boolean egg,
            @RequestParam(defaultValue = "false") boolean dairy) {

        List<RestaurantResponse> response = restaurantService.getRestaurantsByZipAndAllergy(zipCode, peanut, egg, dairy);

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantResponse>> createRestaurant(@RequestBody Restaurant restaurant) {
        try {
            RestaurantResponse response = restaurantService.createRestaurant(restaurant);

            return ResponseEntity.ok(ApiResponse.success("Restaurante criado com sucesso!", response));
        } catch (RestaurantAlreadyExistsException e) {
            return ResponseEntity.status(409).body(ApiResponse.error(e.getMessage()));
        }
    }
}
