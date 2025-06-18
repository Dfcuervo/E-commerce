package com.ecommerce.orderservice.application.mapper;

import com.ecommerce.orderservice.domain.model.Payment;
import com.ecommerce.orderservice.infrastructure.entity.PaymentEntity;

public class PaymentMapper {
    public static PaymentEntity toEntity(Payment payment) {
        return new PaymentEntity(
                payment.getId(),
                payment.getOrderId(),
                payment.getPaymentReference(),
                payment.getStatus()
        );
    }

    public static Payment toDomain(PaymentEntity entity) {
        return new Payment(entity.getId(), entity.getOrderId(), entity.getPaymentReference(), entity.getStatus());
    }
}
