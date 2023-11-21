package com.musinsa.product.global.service.lowestprice.strategy.brand;

import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class BrandTotalLowestPriceUpdateService {

    private final RedisTemplate<String, String> redisTemplate;
    private final BrandLowestPriceStrategyFactory brandLowestPriceStrategyFactory;

    public void updateBrandTotalPrice(Long brandId)
    {
        long totalPrice = 0L;
        String brandTotalKey = RedisKeyGenerator.BrandTotalPrice();
        for (CategoryType type : CategoryType.values())
        {
            String KEY = RedisKeyGenerator.BrandCategory(type);
            String member = redisTemplate.opsForZSet().range(KEY, 0, 0)
                    .stream()
                    .findFirst()
                    .orElse("");

            if (member.isBlank())
            {
                continue;
            }

            totalPrice += redisTemplate.opsForZSet().score(KEY, member).longValue();
        }

        redisTemplate.opsForZSet().add(brandTotalKey, brandId.toString(), totalPrice);
    }

    public void process(KafkaBrandEvent kafkaBrandEvent)
    {
        BrandLowestPriceStrategy strategy = brandLowestPriceStrategyFactory.get(kafkaBrandEvent.brandTaskType());
        strategy.process(kafkaBrandEvent);
    }
}
