package com.ecommerce.orderservice.infrastructure.adapter.persistence;

import com.ecommerce.orderservice.application.mapper.OrderMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        List<OrderEntity> entities = orderJpaRepository.findAll();
        return entities.stream()
                .map(OrderMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        orderJpaRepository.deleteById(id);
    }
}
