package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.mapper.dto.OrderDtoMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetOrdersByCustomerUseCase {

    private final OrderGateway orderGateway;
    private static final Logger log = LoggerFactory.getLogger(GetOrdersByCustomerUseCase.class);

    @Autowired
    public GetOrdersByCustomerUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<OrderResponseDto> getOrdersByCustomerId(Long customerId) {
        log.info("[USECASE] Finding orders with customer ID: {}", customerId);
        List<Order> orders = orderGateway.findByCustomerId(customerId);
        return orders.stream()
                .map(OrderDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
