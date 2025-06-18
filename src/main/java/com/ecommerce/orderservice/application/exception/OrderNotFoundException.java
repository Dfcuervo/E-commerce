package com.ecommerce.orderservice.application.exception;


public class OrderNotFoundException extends OrderServiceException {
    public OrderNotFoundException(Long orderId) {
        super("Order not found with ID: " + orderId);
    }
}
