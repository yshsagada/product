package com.musinsa.product.global.service.kafka.payload;

import com.musinsa.product.event.brand.BrandTaskType;
import lombok.Builder;

@Builder
public record KafkaBrandEvent(
        Long brandId,
        BrandTaskType brandTaskType,
        String brandName
) {}
