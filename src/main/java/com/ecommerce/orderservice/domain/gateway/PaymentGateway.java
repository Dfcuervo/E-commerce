package com.ecommerce.orderservice.domain.gateway;

import com.ecommerce.orderservice.domain.model.Payment;

import java.util.Optional;

public interface PaymentGateway {
    Payment save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);
}
