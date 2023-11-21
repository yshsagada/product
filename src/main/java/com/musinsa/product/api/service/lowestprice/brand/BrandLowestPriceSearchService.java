package com.musinsa.product.api.service.lowestprice.brand;

import com.musinsa.product.api.controller.lowestprice.brand.dto.LowestPriceBrandResponse;
import com.musinsa.product.global.service.redis.RedisZSetService;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 서비스
 */
@RequiredArgsConstructor
@Service
public class BrandLowestPriceSearchService {

    private final BrandRepository brandRepository;
    private final RedisZSetService redisZSetService;

    @Nullable
    public LowestPriceBrandResponse findLowestPriceBrand()
    {
        String brandTotalPriceKey = RedisKeyGenerator.BrandTotalPrice();
        String brandId = redisZSetService.getLowestScoreMember(brandTotalPriceKey);

        if (ObjectUtils.isEmpty(brandId))
        {
            return null;
        }

        Long brandTotalPrice = redisZSetService.getMemberScore(brandTotalPriceKey, brandId);
        List<LowestPriceBrandResponse.BrandCategory> categories = new ArrayList<>();

        Brand brand = brandRepository.findById(Long.valueOf(brandId))
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        for (CategoryType categoryType : CategoryType.values())
        {
            Long lowestPrice = redisZSetService.getLowestMemberPrice(RedisKeyGenerator.BrandCategory(categoryType));

            if (ObjectUtils.isEmpty(lowestPrice))
            {
                continue;
            }

            categories.add(new LowestPriceBrandResponse.BrandCategory(categoryType.getCategoryName(), lowestPrice));
        }

        return LowestPriceBrandResponse.of(brand.getBrandName(), brandTotalPrice, categories);
    }
}
