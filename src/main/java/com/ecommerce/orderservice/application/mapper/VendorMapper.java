package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.Vendor;
import com.ecommerce.orderservice.infrastructure.entity.VendorEntity;

public class VendorMapper {
    public static VendorEntity toEntity(Vendor vendor) {
        return new VendorEntity(vendor.getId(), vendor.getName());
    }

    public static Vendor toDomain(VendorEntity entity) {
        return new Vendor(entity.getId(), entity.getName());
    }
}
