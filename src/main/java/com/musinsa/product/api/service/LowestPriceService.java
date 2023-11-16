package com.musinsa.product.api.service;

import com.musinsa.product.api.controller.dto.BrandLowestPriceResponse;
import com.musinsa.product.api.controller.dto.CategoryLowestPriceResponse;
import com.musinsa.product.domain.brandcategoryprice.BrandCategoryPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LowestPriceService {

    private final BrandCategoryPriceRepository brandCategoryPriceRepository;
    private final CategoryLowestPriceService categoryLowestPriceService;

    public CategoryLowestPriceResponse findCategoryLowestPriceBrand()
    {
        return null;
    }

    public BrandLowestPriceResponse findBrandLowestPrice()
    {
        return BrandLowestPriceResponse.of(brandCategoryPriceRepository.findLowestTotalPrice());
    }
}
