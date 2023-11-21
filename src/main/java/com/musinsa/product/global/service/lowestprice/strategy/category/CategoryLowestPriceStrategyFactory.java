package com.musinsa.product.global.service.lowestprice.strategy.category;

import com.musinsa.product.event.product.ProductTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CategoryLowestPriceStrategyFactory {

    private final Map<ProductTaskType, CategoryLowestPriceRedisStrategy> strategies;

    @Autowired
    public CategoryLowestPriceStrategyFactory(List<CategoryLowestPriceRedisStrategy> categoryLowestRedisPrices)
    {
        strategies = categoryLowestRedisPrices.stream()
                .collect(Collectors.toMap(CategoryLowestPriceRedisStrategy::getProductTaskType, strategy -> strategy));
    }

    public CategoryLowestPriceRedisStrategy get(ProductTaskType productTaskType)
    {
        return strategies.get(productTaskType);
    }
}
