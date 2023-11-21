package com.musinsa.product.api.service.brand;

import com.musinsa.product.api.controller.brand.dto.request.BrandUpdateRequest;
import com.musinsa.product.api.controller.brand.dto.response.BrandUpdatedResponse;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.event.brand.BrandEvent;
import com.musinsa.product.event.brand.BrandTaskType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BrandUpdateService {

    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher publisher;

    public BrandUpdatedResponse update(Long brandId, BrandUpdateRequest request)
    {
        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        brand.update(request.brandName());

        publisher.publishEvent(
                BrandEvent.builder()
                        .brandTaskType(BrandTaskType.UPDATE)
                        .brandId(brand.getBrandId())
                        .brandName(brand.getBrandName())
                        .build()
        );

        return BrandUpdatedResponse.of(brand);
    }
}
