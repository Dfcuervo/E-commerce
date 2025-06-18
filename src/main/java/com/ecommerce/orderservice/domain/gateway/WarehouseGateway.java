package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.Warehouse;

import java.util.List;
import java.util.Optional;

public interface WarehouseGateway {
    Optional<Warehouse> findById(Long id);

    List<Warehouse> findAll();
}
