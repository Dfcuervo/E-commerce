package com.ecommerce.orderservice.infrastructure.adapter.kafka;

import com.ecommerce.orderservice.application.usecase.UpdateOrderStatusUseCase;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;

@Component
public class OrderStatusConsumer {

    private final UpdateOrderStatusUseCase updateOrderStatusUseCase;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger log = LoggerFactory.getLogger(OrderStatusConsumer.class);

    public OrderStatusConsumer(UpdateOrderStatusUseCase updateOrderStatusUseCase) {
        this.updateOrderStatusUseCase = updateOrderStatusUseCase;
    }

    @KafkaListener(topics = "orderStatus", groupId = "order-service")
    public void consume(ConsumerRecord<String, String> record) {
        log.info("Kafka message received on topic [orderStatus]: {}", record.value());
        try {
            Map<String, Object> data = objectMapper.readValue(record.value(), Map.class);

            Long orderId = Long.valueOf(data.get("orderId").toString());
            String statusStr = data.get("status").toString().toUpperCase();

            OrderStatus newStatus = OrderStatus.valueOf(statusStr);

            updateOrderStatusUseCase.updateOrderStatus(orderId, newStatus.name());

            log.info("Order ID {} status updated to: {}", orderId, newStatus);

        } catch (Exception e) {
            log.error("Failed to process Kafka message: {}", e.getMessage(), e);
        }
    }
}
