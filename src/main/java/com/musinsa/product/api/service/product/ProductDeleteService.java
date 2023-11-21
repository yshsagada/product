package com.musinsa.product.api.service.product;

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
import org.springframework.util.ObjectUtils;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductDeleteService {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher publisher;

    public void deleteProduct(Long productId)
    {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PRODUCT));

        product.delete();

        publisher.publishEvent(
                ProductEvent.builder()
                        .productId(product.getProductId())
                        .brandId(product.getBrandId())
                        .productPrice(product.getProductPrice())
                        .categoryType(product.getCategoryType())
                        .productTaskType(ProductTaskType.DELETE)
                        .build()
        );
    }

    public void deleteProduct(Product product)
    {
        if (ObjectUtils.isEmpty(product.getProductId()))
        {
            throw new ApiException(ErrorCode.INVALID_PARAMETER);
        }

        productRepository.delete(product);

        publisher.publishEvent(
                ProductEvent.builder()
                        .productId(product.getProductId())
                        .brandId(product.getBrandId())
                        .productPrice(product.getProductPrice())
                        .categoryType(product.getCategoryType())
                        .productTaskType(ProductTaskType.DELETE)
                        .build()
        );
    }

}
