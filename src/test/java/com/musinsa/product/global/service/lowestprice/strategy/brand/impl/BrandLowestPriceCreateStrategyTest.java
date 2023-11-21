package com.musinsa.product.global.service.lowestprice.strategy.brand.impl;

import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.event.brand.BrandTaskType;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("브랜드 최저 카테고리 총합 키 생성 통합 테스트")
class BrandLowestPriceCreateStrategyTest extends IntegrationTestSupport {

    @Autowired
    BrandLowestPriceCreateStrategy strategy;

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

    @DisplayName("브랜드 생성 이벤트가 발생 되면 브랜드 카테고리 최저가격 총합 키가 생성 된다.")
    @Test
    void test()
    {
        // given
        KafkaBrandEvent kafkaBrandEvent = KafkaBrandEvent.builder()
                .brandId(1L)
                .brandName("brandName")
                .brandTaskType(BrandTaskType.CREATE)
                .build();

        // when
        strategy.process(kafkaBrandEvent);
        Set<String> range = redisTemplate.opsForZSet().range(RedisKeyGenerator.BrandTotalPrice(), 0, 0);

        // then
        assertThat(range).containsOnly("1");
    }
}