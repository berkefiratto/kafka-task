package com.orderservice.api.controller;

import com.common.model.dto.*;

import com.orderservice.api.dto.CreateOrderRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    @Value("${app.kafka.topic-orders:orders.v1}")
    private String topic;

    public OrderController(KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest req) {
        logger.info("Yeni sipariş isteği alındı: userId={}, amount={}", req.userId(), req.amount());

        var event = new OrderCreatedEvent(
                UUID.randomUUID().toString(),
                req.userId(),
                req.amount(),
                Instant.now()
        );

        logger.info("Kafka'ya mesaj gönderiliyor: topic={}, orderId={}, event={}", topic, event.orderId(), event);
        
        try {
            kafkaTemplate.send(topic, event.orderId(), event);
            logger.info("Kafka mesajı başarıyla gönderildi: orderId={}", event.orderId());
        } catch (Exception e) {
            logger.error("Kafka mesajı gönderilirken hata oluştu: orderId={}, error={}", event.orderId(), e.getMessage(), e);
        }

        return ResponseEntity.accepted().body(Map.of("orderId", event.orderId()));
    }

}
