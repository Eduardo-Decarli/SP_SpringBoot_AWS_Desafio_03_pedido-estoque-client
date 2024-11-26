package com.compass.ms_order.web.controller;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.services.OrderServices;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderServices services;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order create) {
        Order client = services.createOrder(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(client);
    }
}
