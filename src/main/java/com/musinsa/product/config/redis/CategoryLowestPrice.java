package com.musinsa.product.config.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLowestPrice {
    private Long price;
    private String brand;
    private Long brandId;
    private Long productId;
}
