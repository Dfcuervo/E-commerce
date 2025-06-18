package com.ecommerce.orderservice.application.usecase;

import com.ecommerce.orderservice.application.dto.OrderItemDto;
import com.ecommerce.orderservice.application.dto.OrderRequestDto;
import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.domain.gateway.OrderGateway;
import com.ecommerce.orderservice.domain.model.Order;
import com.ecommerce.orderservice.domain.model.OrderStatus;
import com.ecommerce.orderservice.domain.service.OrderService;
import com.ecommerce.orderservice.infrastructure.adapter.kafka.OrderEventProducer;
import com.ecommerce.orderservice.infrastructure.adapter.kafka.event.OrderToPaymentEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateOrderUseCaseTest {

    private OrderGateway orderGateway;
    private OrderService orderService;
    private OrderEventProducer orderEventProducer;
    private CreateOrderUseCase createOrderUseCase;

    @BeforeEach
    void setUp() {
        orderGateway = mock(OrderGateway.class);
        orderService = new OrderService();
        orderEventProducer = mock(OrderEventProducer.class);
        createOrderUseCase = new CreateOrderUseCase(orderGateway, orderService, orderEventProducer);
    }

    @Test
    void testCreateOrderAndSendKafkaEvent() {
        OrderRequestDto request = new OrderRequestDto(101L, List.of(new OrderItemDto(null, 1L,
                5L, 2, BigDecimal.valueOf(100.0),3L)));
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        when(orderGateway.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponseDto response = createOrderUseCase.createOrder(request);

        assertEquals(101L, response.getCustomerId());
        assertEquals(1, response.getItems().size());
        assertEquals(BigDecimal.valueOf(200.0), response.getTotalAmount());
        verify(orderGateway).save(orderCaptor.capture());

        Order saved = orderCaptor.getValue();
        assertEquals(OrderStatus.PENDING, saved.getStatus());
        assertEquals(BigDecimal.valueOf(200.0), saved.getTotalAmount());
        verify(orderEventProducer).sendOrderToPayment(any(OrderToPaymentEvent.class));
    }

    @Test
    void testCreateOrderWhenOrderIsNullAndThrowsException() {
        OrderRequestDto request = new OrderRequestDto(
                101L,
                List.of(new OrderItemDto(null, 1L, 5L, 2, BigDecimal.valueOf(100.0), 3L))
        );

        when(orderGateway.save(any())).thenReturn(null);

        assertThrows(IllegalStateException.class, () -> {
            createOrderUseCase.createOrder(request);
        });
        verify(orderEventProducer, never()).sendOrderToPayment(any());
    }
}
