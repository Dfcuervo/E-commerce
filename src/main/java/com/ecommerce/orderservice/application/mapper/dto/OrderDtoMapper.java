package com.ecommerce.orderservice.application.mapper.dto;

import com.ecommerce.orderservice.application.dto.OrderItemDto;
import com.ecommerce.orderservice.application.dto.OrderRequestDto;
import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderItem;
import com.ecommerce.orderservice.domain.model.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDtoMapper {
    public static Order toDomain(OrderRequestDto dto) {
        List<OrderItem> items = dto.getItems().stream()
                .map(OrderItemDtoMapper::toDomain)
                .collect(Collectors.toList());

        return new Order(null, dto.getCustomerId(), items, OrderStatus.PENDING, null, null);
    }

    public static OrderResponseDto toDto(Order domain) {
        OrderResponseDto dto = new OrderResponseDto();
        dto.setId(domain.getId());
        dto.setCustomerId(domain.getCustomerId());
        dto.setCreatedAt(domain.getCreatedAt());
        dto.setTotalAmount(domain.getTotalAmount());
        dto.setStatus(domain.getStatus().name());

        List<OrderItemDto> itemDtos = domain.getItems().stream()
                .map(OrderItemDtoMapper::toDto)
                .collect(Collectors.toList());
        dto.setItems(itemDtos);

        return dto;
    }
}
