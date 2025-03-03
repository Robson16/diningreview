package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.DiningReviewRequest;
import com.codeacademy.diningreview.dto.DiningReviewResponse;
import com.codeacademy.diningreview.service.DiningReviewService;
import com.codeacademy.diningreview.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dining-review")
public class DiningReviewController {
    private final DiningReviewService diningReviewService;

    public DiningReviewController(DiningReviewService diningReviewService) {
        this.diningReviewService = diningReviewService;
    }

    @PostMapping("/{userDisplayName}/{restaurantId}")
    public ResponseEntity<ApiResponse<DiningReviewResponse>> createDiningReview(
            @PathVariable String userDisplayName,
            @PathVariable Long restaurantId,
            @RequestBody @Valid DiningReviewRequest diningReview
    ) {
        DiningReviewResponse response = this.diningReviewService.createDiningReview(
                userDisplayName,
                restaurantId,
                diningReview
        );

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
