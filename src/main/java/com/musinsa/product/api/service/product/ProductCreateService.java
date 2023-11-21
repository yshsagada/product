package com.musinsa.product.api.service.product;

import com.musinsa.product.api.controller.product.dto.request.ProductCreateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductCreatedResponse;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.domain.product.ProductStatus;
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
public class ProductCreateService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher publisher;

    public ProductCreatedResponse createProduct(ProductCreateRequest request)
    {
        Brand brand = brandRepository.findById(request.brandId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        Product product = productRepository.save(
                Product.builder()
                        .productPrice(request.price())
                        .brandId(request.brandId())
                        .categoryType(request.categoryType())
                        .productName(request.productName())
                        .productStatus(ProductStatus.SERVICED)
                        .build()
        );

        publisher.publishEvent(
                ProductEvent.builder()
                        .productId(product.getProductId())
                        .brandName(brand.getBrandName())
                        .productPrice(product.getProductPrice())
                        .brandId(brand.getBrandId())
                        .categoryType(product.getCategoryType())
                        .productTaskType(ProductTaskType.CREATE)
                        .build()
        );

        return ProductCreatedResponse.of(product);
    }
}
