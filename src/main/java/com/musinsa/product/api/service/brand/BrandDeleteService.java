package com.musinsa.product.api.service.brand;

import com.musinsa.product.api.service.product.ProductDeleteService;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.event.brand.BrandEvent;
import com.musinsa.product.event.brand.BrandTaskType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class BrandDeleteService {

    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher publisher;
    private final ProductDeleteService productDeleteService;
    private final ProductRepository productRepository;

    public void delete(Long brandId)
    {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        brand.delete();

        List<Product> deletedProducts = productRepository.findByBrandId(brandId);

        for (Product product : deletedProducts)
        {
            productDeleteService.deleteProduct(product);
        }

        publisher.publishEvent(
                BrandEvent.builder()
                        .brandTaskType(BrandTaskType.DELETE)
                        .brandId(brand.getBrandId())
                        .brandName(brand.getBrandName())
                        .build()
        );
    }
}
