package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderGateway {
    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByCustomerId(Long customerId);

    List<Order> search(String status, Long customerId, String date);

    void delete(Long id);
}
