package com.musinsa.product.domain.product;

import com.musinsa.product.domain.type.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 단위 테스트")
class ProductTest {

    private static Product giventProduct()
    {
        return Product.builder()
            .productName("상품1")
            .productStatus(ProductStatus.SERVICED)
            .productPrice(1000L)
            .categoryType(CategoryType.BAG)
            .brandId(1L)
            .build();
    }

    @DisplayName("삭제된 상품은 상태가 DELETED 로 변경된다.")
    @Test
    void deleteProduct()
    {
        // given
        Product product = giventProduct();

        // when
        product.delete();

        // then
        assertThat(product.isDelete()).isTrue();
    }

    @DisplayName("상품의 이름과 가격을 업데이트 할 수 있다.")
    @Test
    void updateProductName()
    {
        // given
        Product product = giventProduct();

        // when
        product.update(1000L, "변경된 상품 이름");

        // then
        assertThat(product).extracting(Product::getProductName, Product::getProductPrice)
                .contains("변경된 상품 이름", 1000L);
    }
}