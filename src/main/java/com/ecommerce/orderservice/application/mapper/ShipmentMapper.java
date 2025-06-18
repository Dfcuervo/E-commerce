package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.Shipment;
import com.ecommerce.orderservice.infrastructure.entity.ShipmentEntity;

public class ShipmentMapper {
    public static ShipmentEntity toEntity(Shipment shipment) {
        return new ShipmentEntity(shipment.getId(), shipment.getOrderId(), shipment.getWarehouseId(), shipment.getShippedAt(), shipment.getTrackingCode());
    }

    public static Shipment toDomain(ShipmentEntity entity) {
        return new Shipment(entity.getId(), entity.getOrderId(), entity.getWarehouseId(), entity.getShippedAt(), entity.getTrackingCode());
    }
}
