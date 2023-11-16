package com.musinsa.product.domain.product;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, name = "product_price")
    private Long productPrice;

    @Column(nullable = false, name = "category_id")
    private Long categoryId;

    @Column(nullable = false, name = "brand_id")
    private Long brandId;
}

