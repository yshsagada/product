package com.musinsa.product.event.product;

import com.musinsa.product.domain.type.CategoryType;
import lombok.Builder;

@Builder
public record ProductEvent(
        Long productId,
        CategoryType categoryType,
        Long productPrice,
        Long brandId,
        String brandName,
        ProductTaskType productTaskType
) {}
