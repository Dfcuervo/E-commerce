package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.infrastructure.entity.OrderEntity;
import com.ecommerce.orderservice.infrastructure.entity.OrderItemEntity;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static OrderEntity toEntity(Order domain) {
        OrderEntity entity = new OrderEntity();
        entity.setId(domain.getId());
        entity.setCustomerId(domain.getCustomerId());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setStatus(domain.getStatus());
        entity.setTotalAmount(domain.getTotalAmount());
        List<OrderItemEntity> items = domain.getItems().stream()
                .map(item -> {
                    OrderItemEntity itemEntity = new OrderItemEntity();
                    itemEntity.setProductId(item.getProductId());
                    itemEntity.setVendorId(item.getVendorId());
                    itemEntity.setQuantity(item.getQuantity());
                    itemEntity.setUnitPrice(item.getUnitPrice());
                    itemEntity.setWarehouseId(item.getWarehouseId());
                    itemEntity.setOrder(entity);
                    return itemEntity;
                })
                .toList();

        entity.getItems().clear();
        entity.setItems(items);
        return entity;
    }

    public static Order toDomain(OrderEntity entity) {
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                entity.getItems().stream()
                        .map(OrderItemMapper::toDomain)
                        .collect(Collectors.toList()),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getTotalAmount()
        );
    }
}
