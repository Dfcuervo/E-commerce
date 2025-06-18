package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.exception.OrderAlreadyCancelledException;
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
public class CancelOrderUseCase {

    private final OrderGateway orderGateway;
    private static final Logger log = LoggerFactory.getLogger(CancelOrderUseCase.class);

    @Autowired
    public CancelOrderUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public void cancelOrder(Long orderId) {
        log.info("[USECASE] Canceling order with ID: {}", orderId);
        Optional<Order> optionalOrder = orderGateway.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        Order order = optionalOrder.get();

        if (order.getStatus() == OrderStatus.CANCELLED) {
            throw new OrderAlreadyCancelledException(orderId);
        }

        order.setStatus(OrderStatus.CANCELLED);
        log.info("Order successfully cancelled");
        orderGateway.save(order);
    }
}
