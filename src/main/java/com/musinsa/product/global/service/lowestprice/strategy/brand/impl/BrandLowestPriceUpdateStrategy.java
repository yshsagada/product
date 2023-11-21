package com.musinsa.product.global.service.lowestprice.strategy.brand.impl;

import com.musinsa.product.event.brand.BrandTaskType;
import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import com.musinsa.product.global.service.lowestprice.strategy.brand.BrandLowestPriceStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BrandLowestPriceUpdateStrategy implements BrandLowestPriceStrategy {

    @Override
    public void process(KafkaBrandEvent kafkaBrandEvent)
    {
        log.info("BrandLowestPriceUpdateStrategy handle");
    }

    @Override
    public BrandTaskType getTaskType()
    {
        return BrandTaskType.UPDATE;
    }
}
