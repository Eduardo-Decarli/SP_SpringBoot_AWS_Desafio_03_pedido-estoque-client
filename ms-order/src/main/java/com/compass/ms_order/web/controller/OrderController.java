package com.compass.ms_order.web.controller;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.services.OrderServices;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import com.compass.ms_order.web.dto.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderServices services;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateDTO create) {
        OrderResponseDTO order = services.createOrder(create);
        order.add(linkTo(methodOn(OrderController.class).findOrderById(order.getId())).withRel("find your Order by id"));
        order.add(linkTo(methodOn(OrderController.class).findOrderByProtocol(order.getProtocol())).withRel("find your order by protocol"));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@RequestBody OrderCreateDTO update, @PathVariable Long id) {
        OrderResponseDTO order = services.updateOrderById(update, id);
        order.add(linkTo(methodOn(OrderController.class).findOrderById(order.getId())).withRel("find your Order by id"));
        order.add(linkTo(methodOn(OrderController.class).findOrderByProtocol(order.getProtocol())).withRel("find your order by protocol"));
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("historic/byEmail/{email}")
    public ResponseEntity<List<OrderResponseDTO>> findOrdersByEmail(@PathVariable String email) {
        List<OrderResponseDTO> order = services.findAllOrdersByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("historic/byProtocol/{protocol}")
    public ResponseEntity<OrderResponseDTO> findOrderByProtocol(@PathVariable String protocol) {
        OrderResponseDTO order = services.findOrderByProtocol(protocol);
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }

    @GetMapping("historic/byId/{id}")
    public ResponseEntity<OrderResponseDTO> findOrderById(@PathVariable Long id) {
        Order order = services.findOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body(OrderMapper.toDto(order));
    }

    @GetMapping("product/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        ProductResponseDTO product = services.findProductById(id);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        services.deleteOrderById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Deleted successfully");
    }
}
