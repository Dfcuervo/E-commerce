package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Warehouse;

public class WarehouseService {
    public Warehouse createWarehouse(Long id, String name, String location) {
        return new Warehouse(id, name, location);
    }
}
