package com.codeacademy.diningreview.dto;

import com.codeacademy.diningreview.enums.ReviewStatus;

public record DiningReviewResponse(
        String submittedBy,
        Long restaurantId,
        Double peanutScore,
        Double eggScore,
        Double dairyScore,
        String commentary,
        ReviewStatus status
) {
}
