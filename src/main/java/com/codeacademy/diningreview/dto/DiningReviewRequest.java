package com.codeacademy.diningreview.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record DiningReviewRequest(
        @Min(1)
        @Max(5)
        Double peanutScore,

        @Min(1)
        @Max(5)
        Double eggScore,

        @Min(1)
        @Max(5)
        Double dairyScore,

        @Size(max = 500, message = "O comentário deve ter no máximo 500 caracteres.")
        String commentary
) {

}
