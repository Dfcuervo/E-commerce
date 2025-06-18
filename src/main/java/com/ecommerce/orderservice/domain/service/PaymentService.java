package com.ecommerce.orderservice.domain.service;

import com.ecommerce.orderservice.domain.model.Payment;

public class PaymentService {
    public Payment createPayment(Long orderId, String reference, String status) {
        return new Payment(null, orderId, reference, status);
    }

    public void updateStatus(Payment payment, String newStatus) {
        payment.setStatus(newStatus);
    }
}
