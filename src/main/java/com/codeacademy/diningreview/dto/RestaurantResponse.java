package com.codeacademy.diningreview.dto;

import java.math.BigDecimal;

public record RestaurantResponse(
        Long id,
        String name,
        String address,
        String zipCode,
        String cuisineType,
        BigDecimal peanutScore,
        BigDecimal eggScore,
        BigDecimal dairyScore,
        BigDecimal overallScore
) {
}
