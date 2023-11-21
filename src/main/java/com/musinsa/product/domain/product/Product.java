package com.musinsa.product.domain.product;

import com.musinsa.product.domain.BaseTimeEntity;
import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false, name = "product_price")
    private Long productPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "category_type")
    private CategoryType categoryType;

    @Column(nullable = false, name = "brand_id")
    private Long brandId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus = ProductStatus.SERVICED;

    @Column(nullable = false, name = "product_name")
    private String productName;

    @Builder
    public Product(
            Long productPrice,
            CategoryType categoryType,
            Long brandId,
            ProductStatus productStatus,
            String productName
    ) {
        this.productPrice = productPrice;
        this.categoryType = categoryType;
        this.brandId = brandId;
        this.productStatus = productStatus == null ? ProductStatus.SERVICED : productStatus;
        this.productName = productName;
    }

    public void delete()
    {
        if (ProductStatus.DELETED.equals(productStatus))
        {
            throw new ApiException(ErrorCode.NOT_FOUND_PRODUCT);
        }

        this.productStatus = ProductStatus.DELETED;
    }

    private void updatePrice(Long price)
    {
        if (price == null || price <= 0)
        {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }

        this.productPrice = price;
    }

    private void updateProductName(String productName)
    {
        if (productName.isBlank())
        {
            return ;
        }

        this.productName = productName;
    }

    public void update(Long price, String productName)
    {
        updatePrice(price);
        updateProductName(productName);
    }

    public boolean isDelete()
    {
        return ProductStatus.DELETED.equals(this.productStatus);
    }
}

