package com.ecommerce.orderservice.infrastructure.adapter.kafka.event;

public class OrderToPaymentEvent {
    private Long orderId;
    private Long customerId;
    private Double amount;

    public OrderToPaymentEvent() {}

    public OrderToPaymentEvent(Long orderId, Long customerId, Double amount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
