package com.compass.ms_stock.repositories;

import com.compass.ms_stock.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Product, Long> {
}
