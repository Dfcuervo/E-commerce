package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.Shipment;

import java.util.List;

public interface ShipmentGateway {
    Shipment save(Shipment shipment);

    List<Shipment> findByOrderId(Long orderId);
}
