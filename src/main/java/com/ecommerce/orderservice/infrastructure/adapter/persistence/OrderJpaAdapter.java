package com.ecommerce.orderservice.infrastructure.adapter.persistence;

import com.ecommerce.orderservice.application.exception.InvalidOrderStatusException;
import com.ecommerce.orderservice.application.mapper.OrderMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import com.ecommerce.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OrderJpaAdapter implements OrderGateway {

    private final OrderJpaRepository orderJpaRepository;

    @Autowired
    public OrderJpaAdapter(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order save(Order order) {
        OrderEntity entity = OrderMapper.toEntity(order);
        OrderEntity saved = orderJpaRepository.save(entity);
        return OrderMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderJpaRepository.findById(id).map(OrderMapper::toDomain);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        return orderJpaRepository.findByCustomerId(customerId).stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> search(String statusStr, Long customerId, String dateStr) {
        LocalDateTime createdDateTimeStart = null;
        LocalDateTime createdDateTimeEnd = null;
        if (dateStr != null && !dateStr.isBlank()) {
            try {
                LocalDate date = LocalDate.parse(dateStr);
                createdDateTimeStart = date.atStartOfDay();
                createdDateTimeEnd = date.plusDays(1).atStartOfDay();
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid format date.");
            }
        }

        OrderStatus status  = null;
        if (statusStr  != null && !statusStr .isBlank()) {
            try {
                status = OrderStatus.valueOf(statusStr.toUpperCase());
            } catch (IllegalArgumentException ex) {
                throw new InvalidOrderStatusException(statusStr);
            }
        }


        List<OrderEntity> entities = orderJpaRepository.searchOrders(status, customerId,
                createdDateTimeStart, createdDateTimeEnd);

        return entities.stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        orderJpaRepository.deleteById(id);
    }
}
