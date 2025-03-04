package com.codeacademy.diningreview.dto;

import jakarta.persistence.Column;

public record UserResponse(
        String displayName,
        String city,
        String state,
        String zipCode,
        Boolean peanutAllergyInterest,
        Boolean eggAllergyInterest,
        Boolean dairyAllergyInterest
) {
}
