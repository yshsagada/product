package com.musinsa.product.domain.categorylowest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CategoryLowest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryLowestId;

    @Column(nullable = false)
    private Long brandCategoryPriceId;

    private Long categoryId;

    private Long price;
}
