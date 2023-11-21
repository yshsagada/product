package com.musinsa.product.api.controller.product.dto.response;

import com.musinsa.product.domain.product.Product;

public record ProductCreatedResponse(
        String productName,
        Long productId,
        Long price,
        String categoryType
) {
    public static ProductCreatedResponse of(Product product)
    {
        return new ProductCreatedResponse(
                product.getProductName(),
                product.getProductId(),
                product.getProductPrice(),
                product.getCategoryType().getCategoryName()
        );
    }
}
