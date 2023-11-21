package com.musinsa.product.global.service.lowestprice.strategy.category.brand.impl;

import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import com.musinsa.product.global.service.lowestprice.strategy.category.brand.CategoryBrandLowestPriceStrategy;
import com.musinsa.product.global.service.lowestprice.strategy.brand.BrandTotalLowestPriceUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class CategoryBrandLowestPriceCreateStrategy implements CategoryBrandLowestPriceStrategy {

    private final RedisTemplate<String, String> redisTemplate;
    private final BrandTotalLowestPriceUpdateService brandTotalLowestPriceUpdateService;

    @Override
    public void process(KafkaProductEvent productEvent)
    {
        redisTemplate.opsForZSet().add(
                RedisKeyGenerator.BrandCategory(productEvent.categoryType()),
                productEvent.brandId() + ":" + productEvent.productId().toString(),
                productEvent.productPrice()
        );

        brandTotalLowestPriceUpdateService.updateBrandTotalPrice(productEvent.brandId());
    }

    @Override
    public ProductTaskType getProductTaskType()
    {
        return ProductTaskType.CREATE;
    }
}
