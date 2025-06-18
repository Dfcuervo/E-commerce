package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.exception.OrderNotFoundException;
import com.ecommerce.orderservice.application.mapper.dto.OrderDtoMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Optional;

@Component
public class GetOrderByIdUseCase {

    private final OrderGateway orderGateway;
    private static final Logger log = LoggerFactory.getLogger(GetOrderByIdUseCase.class);

    @Autowired
    public GetOrderByIdUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public OrderResponseDto getOrderById(Long orderId) {
        log.info("[USECASE] Finding orders with ID: {}", orderId);
        Optional<Order> optionalOrder = orderGateway.findById(orderId);
        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(orderId);
        }

        return OrderDtoMapper.toDto(optionalOrder.get());
    }
}
