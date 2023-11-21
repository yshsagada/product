package com.musinsa.product.api.service.lowestprice.category;

import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryLowestPriceResponse;
import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryLowestPriceResponse.CategoryLowestPrice;
import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryPriceResponse;
import com.musinsa.product.global.service.redis.RedisZSetService;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.exception.ApiException;
import com.musinsa.product.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis ZSet 을 이용한 카테고리별 상품 가격 순위 관리
 * KEY : category:{카테고리타입},
 * example : category:TOP
 * Member : productId (Long) , Score (Long) : 상품 가격
 */
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class CategoryLowestPriceSearchService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final RedisZSetService redisZSetService;

    public CategoryLowestPriceResponse findAllCategoriesLowestPrice()
    {
        List<CategoryLowestPrice> categoryLowestPrices = new ArrayList<>();
        long totalPrice = 0L;

        for (CategoryType categoryType : CategoryType.values())
        {
            String KEY = RedisKeyGenerator.Category(categoryType);
            String productId = redisZSetService.getLowestScoreMember(KEY);

            if (ObjectUtils.isEmpty(productId))
            {
                continue;
            }

            Product product = productRepository.findById(Long.valueOf(productId))
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PRODUCT));

            totalPrice += product.getProductPrice();

            Brand brand = brandRepository.findById(product.getBrandId())
                    .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

            categoryLowestPrices.add(CategoryLowestPrice.of(
                    categoryType,
                    brand.getBrandName(),
                    product.getProductPrice()
            ));
        }

        return new CategoryLowestPriceResponse(totalPrice, categoryLowestPrices);
    }

    public CategoryPriceResponse findLowestPriceInCategory(CategoryType categoryType)
    {
        String KEY = RedisKeyGenerator.Category(categoryType);

        String lowestProductId = redisZSetService.getLowestScoreMember(KEY);

        if (ObjectUtils.isEmpty(lowestProductId))
        {
            return CategoryPriceResponse.empty(categoryType.getCategoryName());
        }

        String highestProductId = redisZSetService.getHighestScoreMember(KEY);
        assert highestProductId != null;

        Product lowestProduct = productRepository.findById(Long.valueOf(lowestProductId))
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PRODUCT));

        Brand lowestBrand = brandRepository.findById(lowestProduct.getBrandId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        if (highestProductId.equals(lowestProductId))
        {
            CategoryPriceResponse.CategoryPrice categoryPrice = CategoryPriceResponse.create(
                    lowestBrand.getBrandName(),
                    lowestProduct.getProductPrice()
            );
            return new CategoryPriceResponse(categoryType.getCategoryName(), categoryPrice, categoryPrice);
        }

        Product highestProduct = productRepository.findById(Long.valueOf(highestProductId))
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_PRODUCT));

        Brand highestBrand = brandRepository.findById(highestProduct.getBrandId())
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND_BRAND));

        return CategoryPriceResponse.create(
                categoryType.getCategoryName(),
                CategoryPriceResponse.create(
                        lowestBrand.getBrandName(),
                        lowestProduct.getProductPrice()
                ),
                CategoryPriceResponse.create(
                        highestBrand.getBrandName(),
                        highestProduct.getProductPrice()
                )
        );
    }
}
