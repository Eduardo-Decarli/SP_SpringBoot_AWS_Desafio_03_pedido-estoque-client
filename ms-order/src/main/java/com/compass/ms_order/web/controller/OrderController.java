package com.compass.ms_order.web.controller;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.services.OrderServices;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
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
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateDTO create) {
        OrderResponseDTO order = services.createOrder(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderCreateDTO update, @PathVariable Long id) {
        OrderResponseDTO order = services.updateOrderById(update, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("historic/byEmail/{email}")
    public ResponseEntity<List<OrderResponseDTO>> findOrdersByEmail(@PathVariable String email) {
        List<OrderResponseDTO> order = services.findAllOrderByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("historic/byProtocol/{protocol}")
    public ResponseEntity<OrderResponseDTO> findOrderByProtocol(@PathVariable String protocol) {
        OrderResponseDTO order = services.findOrderByProtocol(protocol);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponseDTO> findOrdersByEmail(@PathVariable Long id) {
        ProductResponseDTO product = services.findProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }
}
