package com.musinsa.product.global.service.lowestprice.strategy.brand.impl;

import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.event.brand.BrandTaskType;
import com.musinsa.product.global.service.lowestprice.strategy.brand.BrandLowestPriceStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BrandLowestPriceDeleteStrategy implements BrandLowestPriceStrategy {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void process(KafkaBrandEvent kafkaBrandEvent)
    {
        redisTemplate.opsForZSet().remove(RedisKeyGenerator.BrandTotalPrice(), kafkaBrandEvent.brandId().toString());
    }

    @Override
    public BrandTaskType getTaskType()
    {
        return BrandTaskType.DELETE;
    }
}
