package com.musinsa.product.factory;

import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductStatus;
import com.musinsa.product.domain.type.CategoryType;

public class ProductMockFactory {

    public static Product.ProductBuilder create()
    {
        return Product.builder()
                .productPrice(1000L)
                .brandId(1L)
                .productName("product1")
                .productStatus(ProductStatus.SERVICED)
                .categoryType(CategoryType.BAG)
                ;
    }
}
