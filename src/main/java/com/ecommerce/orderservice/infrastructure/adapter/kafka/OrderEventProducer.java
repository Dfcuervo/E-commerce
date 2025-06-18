package com.ecommerce.orderservice.infrastructure.adapter.kafka;

import com.ecommerce.orderservice.infrastructure.adapter.kafka.event.OrderToPaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class OrderEventProducer {

    private final KafkaTemplate<String, OrderToPaymentEvent> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);

    public OrderEventProducer(KafkaTemplate<String, OrderToPaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderToPayment(OrderToPaymentEvent event) {
        try {
            log.info("Publishing message to Kafka topic [OrderToPayment]: {}", event);
            kafkaTemplate.send("OrderToPayment", event);
            log.info("Successfully published order event with ID: {}", event.getOrderId());
        } catch (Exception e) {
            log.error("Failed to send message to Kafka topic [OrderToPayment]: {}", e.getMessage(), e);
        }
    }
}
