package com.musinsa.product.event.brand;

import com.musinsa.product.global.service.kafka.ProducerService;
import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class BrandEventListener {

    private final ProducerService producerService;

    @Async
    @TransactionalEventListener
    public void handle(BrandEvent brandEvent)
    {
        KafkaBrandEvent kafkaBrandEvent = KafkaBrandEvent.builder()
                .brandTaskType(brandEvent.brandTaskType())
                .brandId(brandEvent.brandId())
                .brandName(brandEvent.brandName())
                .build();

        producerService.producer(kafkaBrandEvent);
    }
}
