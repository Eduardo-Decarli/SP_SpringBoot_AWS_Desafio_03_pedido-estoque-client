package com.compass.ms_order.repositories;

import com.compass.ms_order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByClientEmail(String email);
}
