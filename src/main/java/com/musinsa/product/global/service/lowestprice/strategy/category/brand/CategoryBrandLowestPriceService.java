package com.musinsa.product.global.service.lowestprice.strategy.category.brand;

import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryBrandLowestPriceService {

    private final CategoryBrandLowestPriceStrategyFactory categoryBrandLowestPriceStrategyFactory;

    public void processingBrandLowestPrice(KafkaProductEvent productEvent)
    {
        CategoryBrandLowestPriceStrategy strategy = categoryBrandLowestPriceStrategyFactory
                .get(productEvent.productTaskType());
        strategy.process(productEvent);
    }
}
