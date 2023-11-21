package com.musinsa.product.global.service.lowestprice.strategy.category.impl;

import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import com.musinsa.product.global.service.lowestprice.strategy.category.CategoryLowestPriceRedisStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CategoryLowestPriceCreateStrategy implements CategoryLowestPriceRedisStrategy {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(KafkaProductEvent productEvent)
    {
        log.info("CategoryLowestPriceCreateStrategy handle : {}", productEvent);
        redisTemplate.opsForZSet().add(
                RedisKeyGenerator.Category(productEvent.categoryType()),
                productEvent.productId().toString(),
                productEvent.productPrice()
        );
    }

    @Override
    public ProductTaskType getProductTaskType()
    {
        return ProductTaskType.CREATE;
    }
}
