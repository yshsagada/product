package com.musinsa.product.api.service.product;

import com.musinsa.product.api.controller.product.dto.request.ProductCreateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductCreatedResponse;
import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.type.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("상품 생성 통합 테스트")
@Transactional
class ProductCreateServiceTest extends IntegrationTestSupport {

    @Autowired
    ProductCreateService service;

    @Autowired
    BrandRepository brandRepository;

    @DisplayName("상품 생성")
    @Test
    void createProduct()
    {
        // given
        Brand brand = brandRepository.saveAndFlush(Brand.create("brandName"));
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productName("product1")
                .price(100L)
                .categoryType(CategoryType.BAG)
                .brandId(brand.getBrandId())
                .build();

        // when
        ProductCreatedResponse product = service.createProduct(request);

        // then
        assertThat(product).extracting(
                ProductCreatedResponse::categoryType,
                        ProductCreatedResponse::productName,
                        ProductCreatedResponse::price
                )
                .contains(CategoryType.BAG.getCategoryName(), "product1", 100L);
    }
}