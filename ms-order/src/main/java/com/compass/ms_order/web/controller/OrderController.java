package com.compass.ms_order.web.controller;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.services.OrderServices;
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

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order create) {
        Order order = services.createOrder(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("historic/{email}")
    public ResponseEntity<List<Order>> findOrdersByEmail(@PathVariable String email) {
        List<Order> order = services.findAllOrderByEmail(email);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
}
