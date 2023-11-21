package com.musinsa.product.api.controller.brand.dto.response;

import com.musinsa.product.domain.brand.Brand;

public record BrandCreatedResponse(
    Long brandId,
    String brandName
) {
    public static BrandCreatedResponse of(Brand brand)
    {
        return new BrandCreatedResponse(brand.getBrandId(), brand.getBrandName());
    }
}
