package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.InventoryItem;
import com.ecommerce.orderservice.infrastructure.entity.InventoryItemEntity;

public class InventoryItemMapper {

    public static InventoryItemEntity toEntity(InventoryItem item) {
        return new InventoryItemEntity(
                item.getId(),
                item.getProductId(),
                item.getWarehouseId(),
                item.getAvailableQuantity()
        );
    }

    public static InventoryItem toDomain(InventoryItemEntity entity) {
        return new InventoryItem(
                entity.getId(),
                entity.getProductId(),
                entity.getWarehouseId(),
                entity.getAvailableQuantity()
        );
    }
}
