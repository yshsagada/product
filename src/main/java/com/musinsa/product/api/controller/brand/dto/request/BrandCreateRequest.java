package com.musinsa.product.api.controller.brand.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record BrandCreateRequest(

        @Schema(description = "브랜드 이름", example = "아디다스", required = true)
        @NotBlank(message = "브랜드 이름은 필수입니다.")
        String brandName
) {}
