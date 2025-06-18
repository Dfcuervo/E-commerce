package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderItem;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import com.ecommerce.orderservice.domain.util.OrderCalculator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.ecommerce.orderservice.domain.util.OrderCalculator.calculateTotal;

@Component
public class OrderService {
    public Order createOrder(Long customerId, List<OrderItem> items) {
        BigDecimal total = calculateTotal(items);
        return new Order(null, customerId, items, OrderStatus.PENDING, LocalDateTime.now(), total);
    }

    public Order updateItems(Order order, List<OrderItem> newItems) {
        order.setItems(newItems);
        order.setTotalAmount(OrderCalculator.calculateTotal(newItems));
        return order;
    }
}
