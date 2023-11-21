package com.musinsa.product.global.service.lowestprice.strategy.brand;

import com.musinsa.product.event.brand.BrandTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BrandLowestPriceStrategyFactory {
    private final Map<BrandTaskType, BrandLowestPriceStrategy> strategies;

    @Autowired
    public BrandLowestPriceStrategyFactory(List<BrandLowestPriceStrategy> brandLowestPriceStrategies)
    {
        strategies = brandLowestPriceStrategies.stream()
                .collect(Collectors.toMap(BrandLowestPriceStrategy::getTaskType, strategy -> strategy));
    }

    public BrandLowestPriceStrategy get(BrandTaskType taskType)
    {
        return strategies.get(taskType);
    }
}
