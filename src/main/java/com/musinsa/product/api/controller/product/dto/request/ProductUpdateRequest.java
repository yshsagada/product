package com.musinsa.product.api.controller.product.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record ProductUpdateRequest(
        @Schema(description = "변경할 상품 이름", example = "아디다스")
        String productName,

        @Schema(description = "변경할 상품 가격", example = "10000")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        Long price
) {}
