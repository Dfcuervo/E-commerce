package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.Vendor;

import java.util.List;
import java.util.Optional;

public interface VendorGateway {
    Optional<Vendor> findById(Long id);

    List<Vendor> findAll();
}
