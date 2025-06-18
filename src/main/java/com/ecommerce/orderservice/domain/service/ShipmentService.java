package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Shipment;

import java.time.LocalDateTime;

public class ShipmentService {
    public Shipment createShipment(Long orderId, Long warehouseId, String trackingCode) {
        return new Shipment(null, orderId, warehouseId, LocalDateTime.now(), trackingCode);
    }
}
