package com.musinsa.product.global.config.kafka.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.listener.ListenerExecutionFailedException;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaErrorHandler implements KafkaListenerErrorHandler {

    @Override
    public Object handleError(Message<?> message, ListenerExecutionFailedException exception)
    {
        return null;
    }

    // TODO: 추후 DLQ 처리
    @Override
    public Object handleError(
            Message<?> message,
            ListenerExecutionFailedException exception,
            Consumer<?, ?> consumer,
            Acknowledgment ack)
    {

        log.error("[KafkaErrorHandler] : payload : {}, errorMessage : {}, exception : {}",
                message.getPayload(),
                exception.getMessage(),
                exception.getClass()
        );

        assert ack != null;
        ack.acknowledge();
        return null;
    }
}
