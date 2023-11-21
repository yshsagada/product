package com.musinsa.product.api.service.product;

import com.musinsa.product.api.controller.product.dto.request.ProductUpdateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductUpdatedResponse;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.event.product.ProductEvent;
import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductUpdateService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher publisher;

    public ProductUpdatedResponse updateProduct(Long productId, ProductUpdateRequest request)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PRODUCT));

        Brand brand = brandRepository.findById(product.getBrandId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        product.update(request.price(), request.productName());

        publisher.publishEvent(
                ProductEvent.builder()
                        .productId(product.getProductId())
                        .brandId(product.getBrandId())
                        .productPrice(product.getProductPrice())
                        .categoryType(product.getCategoryType())
                        .brandName(brand.getBrandName())
                        .productTaskType(ProductTaskType.UPDATE)
                        .build()
        );

        return ProductUpdatedResponse.of(product);
    }
}
