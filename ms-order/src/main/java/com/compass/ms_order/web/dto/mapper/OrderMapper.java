package com.compass.ms_order.web.dto.mapper;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static Order toOrder(OrderCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Order.class);
    }

    public static OrderResponseDTO toDto(Order order) {
        return new ModelMapper().map(order, OrderResponseDTO.class);
    }

    public static List<OrderResponseDTO> toListDTO(List<Order> orders) {
        return orders.stream().map(order -> toDto(order)).collect(Collectors.toList());
    }
}
