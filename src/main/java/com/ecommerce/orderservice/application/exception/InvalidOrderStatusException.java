package com.ecommerce.orderservice.application.exception;

public class InvalidOrderStatusException extends OrderServiceException {
    public InvalidOrderStatusException(String status) {
        super("Invalid order status: " + status);
    }
}
