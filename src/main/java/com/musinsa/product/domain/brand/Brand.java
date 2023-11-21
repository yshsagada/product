package com.musinsa.product.domain.brand;

import com.musinsa.product.domain.BaseTimeEntity;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Brand extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long brandId;

    @Column(nullable = false, name = "brand_name")
    private String brandName;

    @Column(nullable = false, name = "brand_status")
    @Enumerated(EnumType.STRING)
    private BrandStatus brandStatus;

    @Builder
    public Brand(String brandName)
    {
        this.brandName = brandName;
        this.brandStatus = BrandStatus.SERVICED;
    }

    public static Brand create(String brandName)
    {
        return new Brand(brandName);
    }

    public void update(String brandName)
    {
        if (brandName.isBlank())
        {
            throw new ApiException(ErrorCode.INVALID_PARAMETER);
        }

        this.brandName = brandName;
    }

    public void delete()
    {
        if (BrandStatus.DELETED.equals(this.brandStatus))
        {
            throw new ApiException(ErrorCode.NOT_FOUND_BRAND);
        }

        this.brandStatus = BrandStatus.DELETED;
    }
}
