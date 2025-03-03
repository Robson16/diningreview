package com.codeacademy.diningreview.controller;

import com.codeacademy.diningreview.dto.DiningReviewRequest;
import com.codeacademy.diningreview.dto.DiningReviewResponse;
import com.codeacademy.diningreview.enums.ReviewStatus;
import com.codeacademy.diningreview.service.DiningReviewService;
import com.codeacademy.diningreview.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dining-reviews")
public class DiningReviewController {
    private final DiningReviewService diningReviewService;

    public DiningReviewController(DiningReviewService diningReviewService) {
        this.diningReviewService = diningReviewService;
    }

    @GetMapping("/pending-approval")
    public ResponseEntity<ApiResponse<List<DiningReviewResponse>>> getAllPendingApproval() {
        List<DiningReviewResponse> response = this.diningReviewService.getAllPendingApproval();
        return ResponseEntity.ok(ApiResponse.success(response));
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

    @PutMapping("/{id}/update-status/{newStatus}")
    public ResponseEntity<ApiResponse<DiningReviewResponse>> updateStatus(
            @PathVariable Long id,
            @PathVariable ReviewStatus newStatus
    ) {
        DiningReviewResponse response = this.diningReviewService.updateStatus(id, newStatus);
        return ResponseEntity.ok(ApiResponse.success("Status atualizado com sucesso!", response));
    }
}
