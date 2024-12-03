package com.compass.ms_order.repositories;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;

import java.util.List;

public interface OrderFunctionsRepository {
    OrderResponseDTO createOrder(OrderCreateDTO createDTO);
    OrderResponseDTO updateOrderById(OrderCreateDTO updateDTO, Long id);
    List<OrderResponseDTO> findAllOrdersByEmail(String email);
    Order findOrderById(Long id);
    ProductResponseDTO findProductById(Long id);
    OrderResponseDTO findOrderByProtocol(String protocol);
    void deleteOrderById(Long id);
}
