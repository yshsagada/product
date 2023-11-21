package com.musinsa.product.api.controller.brand.dto.response;

import com.musinsa.product.domain.brand.Brand;

public record BrandUpdatedResponse(
    Long brandId,
    String brandName
) {
    public static BrandUpdatedResponse of(Brand brand)
    {
        return new BrandUpdatedResponse(brand.getBrandId(), brand.getBrandName());
    }
}
