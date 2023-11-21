package com.musinsa.product.api.controller.brand.dto.response;

import com.musinsa.product.domain.brand.Brand;

public record BrandResponse(
        Long brandId,
        String brandName,
        String brandStatus
) {
    public static BrandResponse of(Brand brand)
    {
        return new BrandResponse(brand.getBrandId(), brand.getBrandName(), brand.getBrandStatus().toString());
    }
}
