package com.musinsa.product.global.service.lowestprice.strategy.category.impl;

import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("카테고리 최저 상품 가격 생성 통합 테스트")
class CategoryLowestPriceCreateStrategyTest extends IntegrationTestSupport {

    @Autowired
    CategoryLowestPriceCreateStrategy strategy;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void setUp()
    {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.flushAll();
                return null;
            }
        });
    }

    @DisplayName("")
    @Test
    void createCategoryLowestPrice()
    {
        // given
        KafkaProductEvent productEvent = KafkaProductEvent.builder()
                .productId(1L)
                .productPrice(10000L)
                .categoryType(CategoryType.BAG)
                .productTaskType(ProductTaskType.CREATE)
                .brandId(1L)
                .build();

        // when
        strategy.process(productEvent);
        Set<String> range = redisTemplate.opsForZSet().range(RedisKeyGenerator.Category(CategoryType.BAG), 0, 0);

        // then
        assertThat(range).containsOnly("1");
    }
}