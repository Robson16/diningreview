package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.RestaurantResponse;
import com.codeacademy.diningreview.model.Restaurant;
import com.codeacademy.diningreview.service.RestaurantService;
import com.codeacademy.diningreview.util.ApiResponse;
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
        List<RestaurantResponse> response = this.restaurantService.getAllRestaurants();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RestaurantResponse>> getRestaurantDetails(@PathVariable Long id) {
        RestaurantResponse response = this.restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<RestaurantResponse>>> getRestaurantsByZipAndAllergy(
            @RequestParam String zipCode,
            @RequestParam(defaultValue = "false") boolean peanut,
            @RequestParam(defaultValue = "false") boolean egg,
            @RequestParam(defaultValue = "false") boolean dairy
    ) {
        List<RestaurantResponse> response = this.restaurantService.getRestaurantsByZipAndAllergy(
                zipCode,
                peanut,
                egg,
                dairy
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<RestaurantResponse>> createRestaurant(@RequestBody Restaurant restaurant) {
        RestaurantResponse response = this.restaurantService.createRestaurant(restaurant);
        return ResponseEntity.ok(ApiResponse.success("Restaurante criado com sucesso!", response));
    }

    @PutMapping("/{restaurantId}/update-scores")
    public ResponseEntity<ApiResponse<RestaurantResponse>> updateRestaurantScores(@PathVariable Long restaurantId) {
        RestaurantResponse response = restaurantService.updateScores(restaurantId);
        return ResponseEntity.ok(ApiResponse.success("Scores atualizados com sucesso!", response));
    }
}
