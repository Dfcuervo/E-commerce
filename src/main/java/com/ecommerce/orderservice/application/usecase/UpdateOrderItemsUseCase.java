package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderItemDto;
import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.exception.OrderNotFoundException;
import com.ecommerce.orderservice.application.mapper.dto.OrderDtoMapper;
import com.ecommerce.orderservice.application.mapper.dto.OrderItemDtoMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderItem;
import com.ecommerce.orderservice.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UpdateOrderItemsUseCase {

    private final OrderGateway orderGateway;
    private final OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(UpdateOrderItemsUseCase.class);

    @Autowired
    public UpdateOrderItemsUseCase(OrderGateway orderGateway, OrderService orderService) {
        this.orderGateway = orderGateway;
        this.orderService = orderService;
    }

    public OrderResponseDto updateOrderItems(Long orderId, List<OrderItemDto> updatedItemsDto) {
        log.info("[USECASE] Updating order ID items: {}", orderId);
        Optional<Order> optionalOrder = orderGateway.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        Order existingOrder = optionalOrder.get();

        List<OrderItem> newItems = updatedItemsDto.stream()
                .map(OrderItemDtoMapper::toDomain)
                .collect(Collectors.toList());
        log.debug("New items: {}", newItems);

        Order updatedOrder = orderService.updateItems(existingOrder, newItems);
        log.info("Order updated with new items: {}", updatedOrder.getId());

        Order savedOrder = orderGateway.save(updatedOrder);

        return OrderDtoMapper.toDto(savedOrder);
    }
}
