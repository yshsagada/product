package com.musinsa.product.api.controller.product.dto.request;

import com.musinsa.product.domain.type.CategoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProductCreateRequest(
        @Schema(description = "상품 가격", example = "1000", required = true)
        @NotNull(message = "상품 가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0 이상이어야 합니다.")
        Long price,

        @Schema(description = "카테고리", example = "TOP|OUTER|BOTTOM|SNEAKERS|BAG|HAT|SOCKS|ACCESSORY", required = true)
        @NotNull(message = "상품 카테고리는 필수입니다.")
        CategoryType categoryType,

        @Schema(description = "브랜드 id", example = "1", required = true)
        @NotNull(message = "상품 브랜드 ID는 필수입니다.")
        Long brandId,

        @Schema(description = "상품 이름", example = "아디다스", required = true)
        @NotBlank(message = "상품 이름은 필수입니다.")
        String productName
) {}
