package com.ecommerce.orderservice.application.mapper.dto;

import com.ecommerce.orderservice.application.dto.OrderItemDto;
import com.ecommerce.orderservice.domain.model.OrderItem;

public class OrderItemDtoMapper {
    public static OrderItem toDomain(OrderItemDto dto) {
        return new OrderItem(
                dto.getId(),
                dto.getProductId(),
                dto.getVendorId(),
                dto.getQuantity(),
                dto.getUnitPrice(),
                dto.getWarehouseId()
        );
    }

    public static OrderItemDto toDto(OrderItem domain) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(domain.getProductId());
        dto.setVendorId(domain.getVendorId());
        dto.setQuantity(domain.getQuantity());
        dto.setUnitPrice(domain.getUnitPrice());
        dto.setWarehouseId(domain.getWarehouseId());
        return dto;
    }
}
