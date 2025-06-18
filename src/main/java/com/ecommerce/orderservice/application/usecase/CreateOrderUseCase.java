package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderRequestDto;
import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.mapper.dto.OrderDtoMapper;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.service.OrderService;
import com.ecommerce.orderservice.infrastructure.adapter.kafka.OrderEventProducer;
import com.ecommerce.orderservice.infrastructure.adapter.kafka.event.OrderToPaymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CreateOrderUseCase {

    private final OrderGateway orderGateway;
    private final OrderService orderService;
    private final OrderEventProducer orderEventProducer;
    private static final Logger log = LoggerFactory.getLogger(CreateOrderUseCase.class);

    @Autowired
    public CreateOrderUseCase(OrderGateway orderGateway, OrderService orderService,
                              OrderEventProducer orderEventProducer) {
        this.orderGateway = orderGateway;
        this.orderService = orderService;
        this.orderEventProducer = orderEventProducer;
    }


    public OrderResponseDto createOrder(OrderRequestDto requestDto) {
        log.info("[USECASE] Initiating sales order creation {}", requestDto.getCustomerId());
        Order rawOrder = OrderDtoMapper.toDomain(requestDto);
        log.debug("Order received: {}", rawOrder);

        Order enrichedOrder = orderService.createOrder(rawOrder.getCustomerId(), rawOrder.getItems());
        log.debug("Enriched order: total={}, ítems={}", enrichedOrder.getTotalAmount(),
                enrichedOrder.getItems().size());

        Order savedOrder = orderGateway.save(enrichedOrder);
        if (savedOrder == null) {
            log.error("Failed to save order: returned null from OrderGateway");
            throw new IllegalStateException("Order could not be saved.");
        }

        OrderToPaymentEvent event = new OrderToPaymentEvent(
                savedOrder.getId(),
                savedOrder.getCustomerId(),
                savedOrder.getTotalAmount().doubleValue()
        );
        orderEventProducer.sendOrderToPayment(event);
        log.info("Event sent to Kafka for ID request: {}", savedOrder.getId());

        return OrderDtoMapper.toDto(savedOrder);
    }
}
