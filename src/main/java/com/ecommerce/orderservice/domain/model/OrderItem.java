package com.ecommerce.orderservice.domain.model;

import java.math.BigDecimal;

public class OrderItem {
    private Long id;
    private Long productId;
    private Long vendorId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private Long warehouseId;

    public OrderItem() {
    }

    public OrderItem(Long id, Long productId, Long vendorId, Integer quantity, BigDecimal unitPrice, Long warehouseId) {
        this.id = id;
        this.productId = productId;
        this.vendorId = vendorId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.warehouseId = warehouseId;
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

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }
}
