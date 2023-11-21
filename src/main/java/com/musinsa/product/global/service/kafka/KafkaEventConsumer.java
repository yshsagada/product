package com.musinsa.product.global.service.kafka;

import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import com.musinsa.product.global.service.lowestprice.strategy.brand.BrandTotalLowestPriceUpdateService;
import com.musinsa.product.global.service.lowestprice.strategy.category.brand.CategoryBrandLowestPriceService;
import com.musinsa.product.global.service.lowestprice.strategy.category.CategoryLowestPriceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaEventConsumer {

    private final CategoryLowestPriceService categoryLowestPriceService;
    private final CategoryBrandLowestPriceService categoryBrandLowestPriceService;
    private final BrandTotalLowestPriceUpdateService brandTotalLowestPriceUpdateService;

    @KafkaListener(
            topics = "product-event",
            groupId = "category-price-group",
            containerFactory = "productEventConsumerContainerFactory",
            concurrency = "2",
            errorHandler = "kafkaErrorHandler"
    )
    public void handleRedisCategoryLowestPrice(@Payload KafkaProductEvent productEvent, Acknowledgment acknowledgment)
    {
        log.info("product-event handle category : {}", productEvent);
        categoryLowestPriceService.processingCategoryLowestPrice(productEvent);
        categoryBrandLowestPriceService.processingBrandLowestPrice(productEvent);
        acknowledgment.acknowledge();
    }

    @KafkaListener(
            topics = "brand-event",
            groupId = "brand-event-group",
            containerFactory = "brandEventConsumerContainerFactory",
            concurrency = "1",
            errorHandler = "kafkaErrorHandler"
    )
    public void brandEventHandle(@Payload KafkaBrandEvent brandEvent, Acknowledgment acknowledgment)
    {
        brandTotalLowestPriceUpdateService.process(brandEvent);
        acknowledgment.acknowledge();
    }
}
