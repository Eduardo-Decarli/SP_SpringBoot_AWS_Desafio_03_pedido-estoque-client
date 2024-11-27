package com.compass.ms_order.web.controller;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.services.OrderServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderServices services;

    @Operation(summary = "Create a new order", description = "Creates a new order in the system.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data", content = @Content)
    })
    @PostMappin
    public ResponseEntity<Order> createOrder(@RequestBody Order create) {
        Order order = services.createOrder(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @Operation(summary = "Find orders by email", description = "Fetches all orders associated with a email.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No orders found for the email")
    })
    @GetMapping("historic/{email}")
    public ResponseEntity<List<Order>> findOrdersByEmail(@PathVariable String email) {
        List<Order> order = services.findAllOrderByEmail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
