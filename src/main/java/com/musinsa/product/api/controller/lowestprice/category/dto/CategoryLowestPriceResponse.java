package com.musinsa.product.api.controller.lowestprice.category.dto;

import com.musinsa.product.domain.type.CategoryType;

import java.util.List;

public record CategoryLowestPriceResponse(
        Long totalPrice,
        List<CategoryLowestPrice> categoryLowestPrices
) {
    public record CategoryLowestPrice(
            String categoryName,
            String brandName,
            Long price
    ) {
        public static CategoryLowestPrice of(CategoryType categoryType, String brandName, Long price)
        {
            return new CategoryLowestPrice(categoryType.getCategoryName(), brandName ,price);
        }
    }
}
