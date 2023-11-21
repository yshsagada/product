package com.musinsa.product.api.service.product;

import com.musinsa.product.api.controller.product.dto.request.ProductUpdateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductUpdatedResponse;
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

@DisplayName("상품 변경 통합 테스트")
@Transactional
class ProductUpdateServiceTest extends IntegrationTestSupport {

    @Autowired
    ProductUpdateService service;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @DisplayName("상품 변경")
    @Test
    void updateProduct()
    {
        // given
        Brand brand = brandRepository.saveAndFlush(Brand.create("brandName"));
        Product product = productRepository.saveAndFlush(
                ProductMockFactory.create().productName("before").productPrice(100L).brandId(brand.getBrandId()).build()
        );

        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .productName("product")
                .price(300L)
                .build();

        // when
        ProductUpdatedResponse updatedProduct = service.updateProduct(product.getProductId(), request);

        // then
        assertThat(updatedProduct).extracting(
                        ProductUpdatedResponse::productName,
                        ProductUpdatedResponse::price
                )
                .contains("product", 300L);
    }
}