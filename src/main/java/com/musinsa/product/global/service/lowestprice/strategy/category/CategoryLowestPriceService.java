package com.musinsa.product.global.service.lowestprice.strategy.category;

import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryLowestPriceService {

    private final CategoryLowestPriceStrategyFactory categoryLowestPriceStrategyFactory;

    public void processingCategoryLowestPrice(KafkaProductEvent productEvent)
    {
        CategoryLowestPriceRedisStrategy strategy = categoryLowestPriceStrategyFactory
                .get(productEvent.productTaskType());
        strategy.process(productEvent);
    }
}
