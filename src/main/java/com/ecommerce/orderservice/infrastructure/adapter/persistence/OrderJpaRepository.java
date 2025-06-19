package com.ecommerce.orderservice.infrastructure.adapter.persistence;

import com.ecommerce.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);
}
