package com.ecommerce.orderservice.infrastructure.adapter.controller;

import com.ecommerce.orderservice.application.dto.*;
import com.ecommerce.orderservice.application.usecase.*;
import com.ecommerce.orderservice.infrastructure.config.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@RestController
@RequestMapping("api/v1/orders")
@Tag(name = "Orders", description = "Operations for managing customer orders")
@SecurityRequirement(name = "BearerAuth")
public class OrderController {
    private final CreateOrderUseCase createOrderUseCase;
    private final UpdateOrderItemsUseCase updateOrderItemsUseCase;
    private final GetOrderByIdUseCase getOrderByIdUseCase;
    private final GetOrdersByCustomerUseCase getOrdersByCustomerUseCase;
    private final SearchOrdersUseCase searchOrdersUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    public OrderController(
            CreateOrderUseCase createOrderUseCase,
            UpdateOrderItemsUseCase updateOrderItemsUseCase,
            GetOrderByIdUseCase getOrderByIdUseCase,
            GetOrdersByCustomerUseCase getOrdersByCustomerUseCase,
            SearchOrdersUseCase searchOrdersUseCase,
            CancelOrderUseCase cancelOrderUseCase
    ) {
        this.createOrderUseCase = createOrderUseCase;
        this.updateOrderItemsUseCase = updateOrderItemsUseCase;
        this.getOrderByIdUseCase = getOrderByIdUseCase;
        this.getOrdersByCustomerUseCase = getOrdersByCustomerUseCase;
        this.searchOrdersUseCase = searchOrdersUseCase;
        this.cancelOrderUseCase = cancelOrderUseCase;
    }

    @Operation(summary = "Create a new order", description = "Creates a new order for a customer with item details.")
    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponseDto>> createOrder(@Valid @RequestBody OrderRequestDto request) {
        log.info("[POST] Creating new sales order: {}", request.getCustomerId());
        OrderResponseDto response = createOrderUseCase.createOrder(request);
        log.info("Order created with ID: {}", response.getId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(response, "success", "Order successfully created"));
    }

    @Operation(summary = "Get order by ID", description = "Returns the order with the specified ID.")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(@PathVariable Long id) {
        log.info("[GET] Order with ID: {}", id);
        OrderResponseDto dto = getOrderByIdUseCase.getOrderById(id);
        return ResponseEntity.ok(new ApiResponse<>(dto, "success", "Order found"));
    }

    @Operation(summary = "Get all orders by customer ID", description = "Returns a list of orders placed by a specific customer.")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getOrderByCustomer(@PathVariable Long customerId) {
        log.info("[GET] Ordering by customer ID: {}", customerId);
        List<OrderResponseDto> orders = getOrdersByCustomerUseCase.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(new ApiResponse<>(orders, "success", "Customer orders obtained"));
    }

    @Operation(summary = "Search orders", description = "Filters orders by optional status, customer ID, and creation date.")
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> searchOrder(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long customerId,
            @RequestParam(required = false) String date
    ) {
        log.info("[GET] Order search: status={}, customerId={}, date={}", status, customerId, date);
        List<OrderResponseDto> results = searchOrdersUseCase.searchOrders(status, customerId, date);
        log.info("{} orders found", results.size());
        return ResponseEntity.ok(new ApiResponse<>(results, "success", "Search result"));
    }

    @Operation(summary = "Update items in an order", description = "Replaces the items of a specific order with a new list.")
    @PutMapping("/{id}/items")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateItems(
            @PathVariable Long id,
            @RequestBody @NotEmpty(message = "Items list must not be empty") List<OrderItemDto> items
    ) {
        log.info("[PUT]  Updating order ID items: {}", id);
        OrderResponseDto updated = updateOrderItemsUseCase.updateOrderItems(id, items);
        log.info("Items updated for order ID: {}", id);
        return ResponseEntity.ok(new ApiResponse<>(updated, "success", "Items updated correctly"));
    }

    @Operation(summary = "Cancel an order", description = "Cancels the order with the specified ID.")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> cancelOrder(@PathVariable Long id) {
        log.info("[DELETE] Canceling order ID: {}", id);
        cancelOrderUseCase.cancelOrder(id);
        log.info("Order successfully cancelled: {}", id);
        return ResponseEntity.ok(new ApiResponse<>(null, "success", "Order cancelled"));
    }
}
