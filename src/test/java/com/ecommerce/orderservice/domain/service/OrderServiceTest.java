package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderItem;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class OrderServiceTest {

    private final OrderService orderService = new OrderService();

    @Test
    void testCreateOrderWhenStatusIsPendingAndTotalAmountIsValid() {
        Long customerId = 100L;
        List<OrderItem> items = List.of(
                new OrderItem(null, 1L, 5L, 2, BigDecimal.valueOf(100.0), 3L),
                new OrderItem(null, 2L, 5L, 1, BigDecimal.valueOf(50.0), 3L)
        );

        Order order = orderService.createOrder(customerId, items);

        assertNotNull(order);
        assertEquals(customerId, order.getCustomerId());
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(2, order.getItems().size());
        assertEquals(BigDecimal.valueOf(250.0), order.getTotalAmount());
        assertNotNull(order.getCreatedAt());
    }

    @Test
    void testUpdateItemsAndRecalculateTotal() {
        Order order = new Order(1L, 100L, List.of(), OrderStatus.PENDING, LocalDateTime.now(),
                BigDecimal.ZERO);

        List<OrderItem> newItems = List.of(
                new OrderItem(null, 3L, 6L, 3, BigDecimal.valueOf(20.0), 9L)
        );

        Order updated = orderService.updateItems(order, newItems);

        assertEquals(1, updated.getItems().size());
        assertEquals(BigDecimal.valueOf(60.0), updated.getTotalAmount());
    }
}
