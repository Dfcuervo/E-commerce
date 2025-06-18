package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.mapper.dto.OrderDtoMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SearchOrdersUseCase {

    private final OrderGateway orderGateway;
    private static final Logger log = LoggerFactory.getLogger(SearchOrdersUseCase.class);

    @Autowired
    public SearchOrdersUseCase(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    public List<OrderResponseDto> searchOrders(String status, Long customerId, String date) {
        log.info("[USECASE] Order search with filters - status: {}, customerId: {}, date: {}",
                status, customerId, date);
        return orderGateway.search(status, customerId, date).stream()
                .map(OrderDtoMapper::toDto)
                .collect(Collectors.toList());
    }
}
