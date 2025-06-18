package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.InventoryItem;

import java.util.List;
import java.util.Optional;

public interface InventoryGateway {
    Optional<InventoryItem> findByProductIdAndWarehouseId(Long productId, Long warehouseId);

    InventoryItem save(InventoryItem inventory);

    List<InventoryItem> findAll();
}
