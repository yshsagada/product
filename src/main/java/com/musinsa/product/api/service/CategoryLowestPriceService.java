package com.musinsa.product.api.service;

import com.musinsa.product.config.redis.CategoryLowestPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CategoryLowestPriceService {

    private final RedisTemplate<String, CategoryLowestPrice> redisTemplate;

    @Autowired
    public CategoryLowestPriceService(
            @Qualifier("categoryLowestPriceRedisTemplate") RedisTemplate<String, CategoryLowestPrice> redisTemplate
    ) {
        this.redisTemplate = redisTemplate;
    }

    public void dd() {
        redisTemplate.opsForZSet().add(
                "KEY",
                new CategoryLowestPrice(1000L, "BBB", 1L, 1L),
                1000L
        );
    }
}
