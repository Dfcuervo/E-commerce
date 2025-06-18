package com.ecommerce.orderservice.domain.model;

public class InventoryItem {
    private Long id;
    private Long productId;
    private Long warehouseId;
    private Integer availableQuantity;

    public InventoryItem() {
    }

    public InventoryItem(Long id, Long productId, Long warehouseId, Integer availableQuantity) {
        this.id = id;
        this.productId = productId;
        this.warehouseId = warehouseId;
        this.availableQuantity = availableQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
