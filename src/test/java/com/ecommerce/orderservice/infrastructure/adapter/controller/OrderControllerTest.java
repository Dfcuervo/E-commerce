package com.ecommerce.orderservice.infrastructure.adapter.controller;

import com.ecommerce.orderservice.application.dto.OrderItemDto;
import com.ecommerce.orderservice.application.dto.OrderRequestDto;
import com.ecommerce.orderservice.application.dto.OrderResponseDto;
import com.ecommerce.orderservice.application.usecase.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(OrderController.class)
@AutoConfigureMockMvc(addFilters = false)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean private CreateOrderUseCase createOrderUseCase;
    @MockBean private UpdateOrderItemsUseCase updateOrderItemsUseCase;
    @MockBean private GetOrderByIdUseCase getOrderByIdUseCase;
    @MockBean private GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;
    @MockBean private SearchOrdersUseCase searchOrdersUseCase;
    @MockBean private CancelOrderUseCase cancelOrderUseCase;

    @Test
    void testCreateOrderAndThenReturnsSuccess() throws Exception {
        OrderItemDto item = new OrderItemDto(null,1L,5L,2,
                BigDecimal.valueOf(100.0),3L);
        OrderRequestDto request = new OrderRequestDto(101L, List.of(item));
        OrderResponseDto response = new OrderResponseDto(1L,101L, List.of(item),"PENDING",
                LocalDateTime.now(), BigDecimal.valueOf(200.0));

        when(createOrderUseCase.createOrder(any())).thenReturn(response);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.message", containsString("created")))
                .andExpect(jsonPath("$.data.id", is(1)))
                .andExpect(jsonPath("$.data.customerId", is(101)))
                .andExpect(jsonPath("$.data.totalAmount", is(200.0)))
                .andExpect(jsonPath("$.data.status", is("PENDING")));
        verify(createOrderUseCase).createOrder(any());
    }

    @Test
    void testCreateOrderAndThenReturnsBadRequestWhenItemsIsNull() throws Exception {
        OrderRequestDto invalidRequest = new OrderRequestDto(101L, null);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.data").doesNotExist());
        verify(createOrderUseCase, never()).createOrder(any());
    }

    @Test
    void testGetOrderByIdAndThenReturnsSuccess() throws Exception {
        OrderResponseDto response = new OrderResponseDto(1L, 101L, List.of(), "PENDING",
                LocalDateTime.now(), BigDecimal.TEN);
        when(getOrderByIdUseCase.getOrderById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data.id", is(1)));
        verify(getOrderByIdUseCase).getOrderById(1L);
    }

    @Test
    void testGetOrderByIdAndThenReturnsNotFound() throws Exception {
        when(getOrderByIdUseCase.getOrderById(99L)).thenThrow(new RuntimeException("Order not found"));

        mockMvc.perform(get("/api/v1/orders/99"))
                .andExpect(status().is5xxServerError());
        verify(getOrderByIdUseCase).getOrderById(99L);
    }

    @Test
    void testGetOrdersByCustomerWhenReturnsAList() throws Exception {
        OrderResponseDto order = new OrderResponseDto(1L, 101L, List.of(), "PAID",
                LocalDateTime.now(), BigDecimal.TEN);
        when(getOrdersByCustomerUseCase.getOrdersByCustomerId(101L)).thenReturn(List.of(order));

        mockMvc.perform(get("/api/v1/orders/customer/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].customerId", is(101)));
        verify(getOrdersByCustomerUseCase).getOrdersByCustomerId(101L);
    }

    @Test
    void testSearchOrdersWithParams() throws Exception {
        OrderResponseDto order = new OrderResponseDto(1L, 101L, List.of(), "PAID",
                LocalDateTime.now(), BigDecimal.TEN);
        when(searchOrdersUseCase.searchOrders("PAID", 101L,
                "2025-06-17")).thenReturn(List.of(order));

        mockMvc.perform(get("/api/v1/orders/search")
                        .param("status", "PAID")
                        .param("customerId", "101")
                        .param("date", "2025-06-17"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.data[0].status", is("PAID")));
        verify(searchOrdersUseCase).searchOrders("PAID", 101L, "2025-06-17");
    }

    @Test
    void testUpdateItemsReturnsUpdatedOrder() throws Exception {
        List<OrderItemDto> items = List.of(new OrderItemDto(null, 1L, 5L, 2,
                BigDecimal.valueOf(100), 3L));
        OrderResponseDto response = new OrderResponseDto(1L, 101L, items, "PENDING",
                LocalDateTime.now(), BigDecimal.valueOf(200));
        when(updateOrderItemsUseCase.updateOrderItems(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/v1/orders/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(items)))
                .andExpect(status().isOk());
        verify(updateOrderItemsUseCase).updateOrderItems(eq(1L), any());
    }

    @Test
    void testUpdateItemsWithEmptyListReturnsBadRequest() throws Exception {
        List<OrderItemDto> invalidItems = Collections.emptyList();

        mockMvc.perform(put("/api/v1/orders/1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidItems)))
                .andExpect(status().is5xxServerError());
        verify(updateOrderItemsUseCase, never()).updateOrderItems(anyLong(), any());
    }

    @Test
    void testCancelOrderAndThenReturnsSuccess() throws Exception {
        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")));
        verify(cancelOrderUseCase).cancelOrder(1L);
    }

    @Test
    void testCancelOrderWithNonexistentIdAndThenReturnsNotFound() throws Exception {
        doThrow(new RuntimeException("Order not found"))
                .when(cancelOrderUseCase).cancelOrder(999L);

        mockMvc.perform(delete("/api/v1/orders/999"))
                .andExpect(status().is5xxServerError());
        verify(cancelOrderUseCase).cancelOrder(999L);
    }
}