package com.musinsa.product.domain.brand;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("브랜드 단위 테스트")
class BrandTest {
    
    @DisplayName("브랜드를 삭제하면 DELETED 상태가 된다.")
    @Test
    void deleteBrand()
    {
        // given
        Brand brand = Brand.create("brandName");
        assertThat(brand.getBrandStatus()).isEqualTo(BrandStatus.SERVICED);

        // when
        brand.delete();
        assertThat(brand.getBrandStatus()).isEqualTo(BrandStatus.DELETED);
    }
}