package com.musinsa.product.global.service.kafka.payload;

import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.event.product.ProductTaskType;
import lombok.Builder;

@Builder
public record KafkaProductEvent(
        Long productId,
        CategoryType categoryType,
        Long productPrice,
        Long brandId,
        String brandName,
        ProductTaskType productTaskType
) {}
