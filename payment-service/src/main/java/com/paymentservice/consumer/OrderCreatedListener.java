package com.paymentservice.consumer;

import com.common.model.dto.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderCreatedListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderCreatedListener.class);

    @KafkaListener(topics = "${app.kafka.topic-orders:orders.v1}")
    public void onOrderCreated(String message) {
        logger.info("Payment-service: Yeni sipariş alındı -> {}", message);
    }

}
