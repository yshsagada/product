package com.musinsa.product.domain.brandcategoryprice;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
@Table(indexes = @Index(name = "idx_total_price", columnList = "total_price"))
public class BrandCategoryPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_category_price_id")
    private Long brandCategoryPriceId;

    @Column(nullable = false, name = "brand_id")
    private Long brandId;

    @Column(nullable = false, name = "brand_name")
    private String brandName;

    @Column(nullable = false)
    private Long topPrice;

    @Column(nullable = false)
    private Long outerPrice;

    @Column(nullable = false)
    private Long bottomPrice;

    @Column(nullable = false)
    private Long sneakersPrice;

    @Column(nullable = false)
    private Long bagPrice;

    @Column(nullable = false)
    private Long hatPrice;

    @Column(nullable = false)
    private Long socksPrice;

    @Column(nullable = false)
    private Long accessoryPrice;

    @Column(nullable = false, name = "total_price")
    private Long totalPrice;

    @Builder
    public BrandCategoryPrice(
            Long brandCategoryPriceId,
            Long brandId,
            String brandName,
            Long topPrice,
            Long outerPrice,
            Long bottomPrice,
            Long sneakersPrice,
            Long bagPrice,
            Long hatPrice,
            Long socksPrice,
            Long accessoryPrice,
            Long totalPrice
    ) {
        this.brandCategoryPriceId = brandCategoryPriceId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.topPrice = topPrice;
        this.outerPrice = outerPrice;
        this.bottomPrice = bottomPrice;
        this.sneakersPrice = sneakersPrice;
        this.bagPrice = bagPrice;
        this.hatPrice = hatPrice;
        this.socksPrice = socksPrice;
        this.accessoryPrice = accessoryPrice;
        this.totalPrice = totalPrice;
    }

    @PrePersist
    public void updateTotalPrice()
    {
        this.totalPrice =
                this.topPrice
                + this.outerPrice
                + this.bottomPrice
                + this.sneakersPrice
                + this.bagPrice
                + this.hatPrice
                + this.socksPrice
                + this.accessoryPrice;
    }
}
