package com.ecommerce.orderservice.application.exception;

public class OrderAlreadyCancelledException extends OrderServiceException {
    public OrderAlreadyCancelledException(Long orderId) {
        super("Order with ID: " + orderId + " was canceled.");
    }
}
