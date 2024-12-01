package com.compass.ms_stock.repositories;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;

import java.util.List;

public interface StockFunctionsRepository {
    ProductResponseDTO createProductInStock(ProductCreateDTO create);
    List<ProductResponseDTO> findAllProducts();
    Product findProductById(Long id);
    Product findProductByName(String name);
    ProductResponseDTO updateProductInStock(ProductCreateDTO update, Long id);
    ProductResponseDTO removeQuantityByName(Integer quantity, String name);
    ProductResponseDTO addQuantityByName(Integer quantity, String name);
}
