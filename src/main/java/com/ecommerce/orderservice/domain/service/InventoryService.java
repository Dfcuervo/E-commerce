package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.InventoryItem;

public class InventoryService {
    public InventoryItem createInventoryItem(Long productId, Long warehouseId, Integer quantity) {
        return new InventoryItem(null, productId, warehouseId, quantity);
    }

    public void updateQuantity(InventoryItem item, int newQuantity) {
        item.setAvailableQuantity(newQuantity);
    }
}
