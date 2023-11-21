package com.musinsa.product.global;

import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.global.config.redis.RedisKeyGenerator;
import com.musinsa.product.global.service.lowestprice.strategy.brand.BrandTotalLowestPriceUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DataInitService {

    private final RedisTemplate<String, String> redisTemplate;
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final BrandTotalLowestPriceUpdateService totalLowestPriceUpdateService;

    public void localRedisInit()
    {
        redisTemplate.execute((RedisCallback<Object>) connection -> {
            connection.flushAll();
            return null;
        });
    }

    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void init()
    {
        localRedisInit();
        List<Product> products = productRepository.findAll();
        for (Product product : products)
        {
            redisTemplate.opsForZSet().add(
                    RedisKeyGenerator.Category(product.getCategoryType()),
                            product.getProductId().toString(),
                            product.getProductPrice()
            );

            redisTemplate.opsForZSet().add(
                    RedisKeyGenerator.BrandCategory(product.getCategoryType()),
                    product.getProductId().toString() + ":" + product.getBrandId(),
                        product.getProductPrice()
            );
        }

        List<Brand> brands = brandRepository.findAll();

        for (Brand brand : brands)
        {
            redisTemplate.opsForZSet().add(RedisKeyGenerator.BrandTotalPrice(), brand.getBrandId().toString(), 0L);
        }

        for (Brand brand : brands)
        {
            totalLowestPriceUpdateService.updateBrandTotalPrice(brand.getBrandId());
        }
    }
}
