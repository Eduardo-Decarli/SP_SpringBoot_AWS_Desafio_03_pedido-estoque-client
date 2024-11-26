package com.compass.ms_stock.services;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.exceptions.EntityNotFoundException;
import com.compass.ms_stock.exceptions.ErrorQuantityBelowZero;
import com.compass.ms_stock.repositories.StockRepository;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Log4j2
public class StockService {

    private final StockRepository repository;

    public void createProductInStock(ProductCreateDTO create) {
        Product product = StockDTO.toProduct(create);
        repository.save(product);
    }

    public List<ProductResponseDTO> findAllProducts() {
        log.info("Search all products");
        List<Product> products = repository.findAll();
        if(products.isEmpty()) {
            throw new EntityNotFoundException("There are not products in stock");
        }
        return StockDTO.toListDTO(products);
    }

    public Product findProductById(Long id) {
        log.info("Search a product by id: " + id);
        Product product = repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found by this id " + id)
        );
        return product;
    }

    public Product findProductByName(String name) {
        log.info("Search a product by name: " + name);
        Product product = repository.findProductByName(name);
        if(product == null){
            throw new EntityNotFoundException("Product not found by this name " + name);
        }
        return product;
    }

    public void removeQuantityByName(Integer quantity, String name) {
        Product product = findProductByName(name);
        product.setQuantity(product.getQuantity() - quantity);
        if(product.getQuantity() < 0) {
            throw new ErrorQuantityBelowZero("The quantity is not below zero");
        }
        log.info("Remove " + quantity + " of product by name " + name);
        repository.save(product);
    }

    public void addQuantityByName(Integer quantity, String name) {
        Product product = findProductByName(name);
        product.setQuantity(product.getQuantity() + quantity);
        log.info("add " + quantity + " of product by name " + name);
        repository.save(product);
    }
}
