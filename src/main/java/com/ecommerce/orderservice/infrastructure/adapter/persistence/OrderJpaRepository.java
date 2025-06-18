package com.ecommerce.orderservice.infrastructure.adapter.persistence;

import com.ecommerce.orderservice.domain.model.OrderStatus;
import com.ecommerce.orderservice.infrastructure.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustomerId(Long customerId);

    @Query("""
    SELECT o FROM OrderEntity o
    WHERE (:status IS NULL OR o.status = :status)
    AND (:customerId IS NULL OR o.customerId = :customerId)
    AND (
        :createdDateTimeStart IS NULL OR 
        (o.createdAt >= :createdDateTimeStart AND o.createdAt < :createdDateTimeEnd)
    )
""")
    List<OrderEntity> searchOrders(
            @Param("status") OrderStatus status,
            @Param("customerId") Long customerId,
            @Param("createdDateTimeStart") LocalDateTime createdDateTimeStart,
            @Param("createdDateTimeEnd") LocalDateTime createdDateTimeEnd
    );
}
