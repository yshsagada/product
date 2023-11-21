package com.musinsa.product.api.service.brand;

import com.musinsa.product.api.controller.brand.dto.request.BrandCreateRequest;
import com.musinsa.product.api.controller.brand.dto.response.BrandCreatedResponse;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.event.brand.BrandEvent;
import com.musinsa.product.event.brand.BrandTaskType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class BrandCreateService {

    private final BrandRepository brandRepository;
    private final ApplicationEventPublisher publisher;

    public BrandCreatedResponse create(BrandCreateRequest request)
    {
        Brand brand = Brand.create(request.brandName());
        brandRepository.save(brand);

        publisher.publishEvent(
                BrandEvent.builder()
                        .brandTaskType(BrandTaskType.CREATE)
                        .brandId(brand.getBrandId())
                        .brandName(brand.getBrandName())
                        .build()
        );

        return BrandCreatedResponse.of(brand);
    }
}
