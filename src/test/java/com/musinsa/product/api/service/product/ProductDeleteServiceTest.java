package com.musinsa.product.api.service.product;

import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.factory.ProductMockFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 삭제 통합 테스트")
@Transactional
class ProductDeleteServiceTest extends IntegrationTestSupport {

    @Autowired
    ProductDeleteService service;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;


    @DisplayName("상품 삭")
    @Test
    void updateProduct()
    {
        // given
        Brand brand = brandRepository.saveAndFlush(Brand.create("brandName"));
        Product product = productRepository.saveAndFlush(
                ProductMockFactory.create().productName("before").productPrice(100L).brandId(brand.getBrandId()).build()
        );

        // when
        service.deleteProduct(product.getProductId());

        // then
        assertThat(product.isDelete()).isTrue();
    }
}