package com.ecommerce.orderservice.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequestDto {
    @NotNull
    private Long customerId;

    @NotEmpty
    @NotNull
    @Valid
    private List<OrderItemDto> items;

    public OrderRequestDto() {
    }

    public OrderRequestDto(Long customerId, List<OrderItemDto> items) {
        this.customerId = customerId;
        this.items = items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItemDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDto> items) {
        this.items = items;
    }
}

