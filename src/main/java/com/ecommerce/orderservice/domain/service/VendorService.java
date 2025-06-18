package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Vendor;

public class VendorService {
    public Vendor createVendor(Long id, String name) {
        return new Vendor(id, name);
    }
}
