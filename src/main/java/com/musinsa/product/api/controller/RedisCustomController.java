package com.musinsa.product.api.controller;

import com.musinsa.product.config.redis.CategoryLowestPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/redis")
public class RedisCustomController {

    private static final String KEY = "category:TOP";
    private static final String KEY2 = "category:BOTTOM";
    private static final String KEY3 = "category:OUTER";

    private final RedisTemplate<String, CategoryLowestPrice> categoryTemplate;

    @PostMapping
    public void redisTest()
    {
        categoryTemplate.opsForZSet().add(
                KEY,
                new CategoryLowestPrice(1000L, "BBB", 1L, 1L),
                1000L
        );

        categoryTemplate.opsForZSet().add(
                KEY2,
                new CategoryLowestPrice(500L, "ADIDAS", 1L, 1L),
                500L
        );

        categoryTemplate.opsForZSet().add(
                KEY3,
                new CategoryLowestPrice(400L, "ADIDAS", 1L, 1L),
                400L
        );
    }
}
