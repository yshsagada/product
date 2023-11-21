package com.musinsa.product.global.service.kafka;

import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private static final String PRODUCT_EVENT_TOPIC_NAME = "product-event";
    private static final String BRAND_EVENT_TOPIC_NAME = "brand-event";

    private final KafkaTemplate<String, KafkaProductEvent> productEventKafkaTemplate;
    private final KafkaTemplate<String, KafkaBrandEvent> brandEventKafkaTemplate;

    @Autowired
    public ProducerService(
            @Qualifier("productEventTemplate") KafkaTemplate<String, KafkaProductEvent> productEventKafkaTemplate,
            @Qualifier("brandEventTemplate") KafkaTemplate<String, KafkaBrandEvent> brandEventKafkaTemplate)
    {
        this.productEventKafkaTemplate = productEventKafkaTemplate;
        this.brandEventKafkaTemplate = brandEventKafkaTemplate;
    }

    public void producer(KafkaProductEvent productEvent)
    {
        productEventKafkaTemplate.send(PRODUCT_EVENT_TOPIC_NAME, productEvent.productId().toString(), productEvent);
    }

    public void producer(KafkaBrandEvent brandEvent)
    {
        brandEventKafkaTemplate.send(BRAND_EVENT_TOPIC_NAME, brandEvent.brandId().toString(), brandEvent);
    }
}
