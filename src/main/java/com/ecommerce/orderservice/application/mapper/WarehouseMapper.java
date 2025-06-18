package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.Warehouse;
import com.ecommerce.orderservice.infrastructure.entity.WarehouseEntity;

public class WarehouseMapper {
    public static WarehouseEntity toEntity(Warehouse warehouse) {
        return new WarehouseEntity(warehouse.getId(), warehouse.getName(), warehouse.getLocation());
    }

    public static Warehouse toDomain(WarehouseEntity entity) {
        return new Warehouse(entity.getId(), entity.getName(), entity.getLocation());
    }
}
