package com.musinsa.product.api.controller.dto;

import java.util.List;

public record CategoryLowestPriceResponse(
        Long totalPrice,
        List<CategoryLowestPrice> categoryLowestPrices
) {
    public record CategoryLowestPrice(
            String categoryName,
            String brandName,
            Long price
    ) {}
}
