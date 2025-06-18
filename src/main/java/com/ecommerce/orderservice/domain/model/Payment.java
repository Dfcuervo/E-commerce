package com.ecommerce.orderservice.domain.model;

public class Payment {
    private Long id;
    private Long orderId;
    private String paymentReference;
    private String status;

    public Payment() {
    }

    public Payment(Long id, Long orderId, String paymentReference, String status) {
        this.id = id;
        this.orderId = orderId;
        this.paymentReference = paymentReference;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
