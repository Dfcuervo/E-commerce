package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.OrderItem;
import com.ecommerce.orderservice.infrastructure.entity.OrderItemEntity;

public class OrderItemMapper {
    public static OrderItemEntity toEntity(OrderItem item) {
        return OrderItemEntity.builder()
                .productId(item.getProductId())
                .vendorId(item.getVendorId())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .warehouseId(item.getWarehouseId())
                .build();
    }

    public static OrderItem toDomain(OrderItemEntity entity) {
        return new OrderItem(
                entity.getId(),
                entity.getProductId(),
                entity.getVendorId(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getWarehouseId()
        );
    }
}
