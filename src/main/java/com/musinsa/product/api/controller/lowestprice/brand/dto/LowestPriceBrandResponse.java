package com.musinsa.product.api.controller.lowestprice.brand.dto;

import java.util.List;

public record LowestPriceBrandResponse(
        String brandName,
        Long totalPrice,
        List<BrandCategory> categories
) {
    public static LowestPriceBrandResponse of(
            String brandName,
            Long totalPrice,
            List<BrandCategory> categories
    ) {
        return new LowestPriceBrandResponse(brandName, totalPrice, categories);
    }

    public record BrandCategory(
            String categoryName,
            Long price
    ) {}
}
