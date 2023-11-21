package com.musinsa.product.api.controller.product.dto.response;

import com.musinsa.product.domain.product.Product;

public record ProductUpdatedResponse(
        String productName,
        Long productId,
        Long price,
        String categoryType
) {
    public static ProductUpdatedResponse of(Product product)
    {
        return new ProductUpdatedResponse(
                product.getProductName(),
                product.getProductId(),
                product.getProductPrice(),
                product.getCategoryType().getCategoryName()
        );
    }
}
