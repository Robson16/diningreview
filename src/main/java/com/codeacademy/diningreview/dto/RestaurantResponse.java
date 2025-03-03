package com.codeacademy.diningreview.dto;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String zipCode,
        String cuisineType,
        Double peanutScore,
        Double eggScore,
        Double dairyScore,
        Double overallScore
) {
}
