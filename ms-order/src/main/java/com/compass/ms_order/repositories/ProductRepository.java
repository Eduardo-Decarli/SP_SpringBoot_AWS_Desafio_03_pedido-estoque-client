package com.compass.ms_order.repositories;

import com.compass.ms_order.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
