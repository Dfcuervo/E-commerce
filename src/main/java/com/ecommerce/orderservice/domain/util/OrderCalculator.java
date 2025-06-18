package com.ecommerce.orderservice.domain.util;

import com.ecommerce.orderservice.domain.model.OrderItem;

import java.math.BigDecimal;
import java.util.List;

public class OrderCalculator {

    private OrderCalculator() {}

    public static BigDecimal calculateTotal(List<OrderItem> items) {
        return items.stream()
                .map(item -> item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
