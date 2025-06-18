package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.exception.InvalidOrderStatusException;
import com.ecommerce.orderservice.application.exception.OrderNotFoundException;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Component
public class UpdateOrderStatusUseCase {

    private final OrderGateway orderGateway;
    private static final Logger log = LoggerFactory.getLogger(UpdateOrderStatusUseCase.class);

    @Autowired
    public UpdateOrderStatusUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public void updateOrderStatus(Long orderId, String newStatusStr) {
        log.info("[USECASE] Updating orders with ID: {}", orderId);
        Optional<Order> optionalOrder = orderGateway.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        Order existingOrder = optionalOrder.get();

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(newStatusStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidOrderStatusException(newStatusStr);
        }

        existingOrder.setStatus(newStatus);
        log.info("[USECASE] Users updated successfully by state");
        orderGateway.save(existingOrder);
    }
}
