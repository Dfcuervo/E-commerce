package com.ecommerce.orderservice.domain.model;

import java.time.LocalDateTime;

public class Shipment {
    private Long id;
    private Long orderId;
    private Long warehouseId;
    private LocalDateTime shippedAt;
    private String trackingCode;

    public Shipment() {
    }

    public Shipment(Long id, Long orderId, Long warehouseId, LocalDateTime shippedAt, String trackingCode) {
        this.id = id;
        this.orderId = orderId;
        this.warehouseId = warehouseId;
        this.shippedAt = shippedAt;
        this.trackingCode = trackingCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(Long warehouseId) {
        this.warehouseId = warehouseId;
    }

    public LocalDateTime getShippedAt() {
        return shippedAt;
    }

    public void setShippedAt(LocalDateTime shippedAt) {
        this.shippedAt = shippedAt;
    }

    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }
}
