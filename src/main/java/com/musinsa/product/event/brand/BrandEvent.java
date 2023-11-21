package com.musinsa.product.event.brand;

import lombok.Builder;

@Builder
public record BrandEvent(
        Long brandId,
        BrandTaskType brandTaskType,
        String brandName
) {}
