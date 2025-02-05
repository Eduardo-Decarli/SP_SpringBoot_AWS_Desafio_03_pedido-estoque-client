package com.compass.ms_stock.services;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.exceptions.DataUniqueViolationException;
import com.compass.ms_stock.exceptions.EntityNotFoundException;
import com.compass.ms_stock.exceptions.ErrorQuantityBelowZero;
import com.compass.ms_stock.repositories.StockFunctionsRepository;
import com.compass.ms_stock.repositories.StockRepository;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
@Log4j2
public class StockService implements StockFunctionsRepository {

    private final StockRepository repo;

    public ProductResponseDTO createProductInStock(ProductCreateDTO create) {
        if(repo.findProductByName(create.getName().toUpperCase()) != null) {
            log.error("Error the product already there was found in the stock, error DataUniqueViolationException");
            throw new DataUniqueViolationException("There is already a product registered with that name");
        }
        Product product = StockMapper.toProduct(create);
        product.setName(product.getName().toUpperCase());
        if(product.getQuantity() <= 0) {
            log.error("Error quantity below zero in create a new product, quantity can't be below zero, error ErrorQuantityBelowZero");
            throw new ErrorQuantityBelowZero("The product cannot have a quantity less than zero");
        }
        log.info("Saving a new product in stock: " + product);
        product = repo.save(product);
        return StockMapper.toDto(product);
    }

    public List<ProductResponseDTO> findAllProducts() {
        log.info("Search all products in stock");
        List<Product> products = repo.findAll();
        if(products.isEmpty()) {
            log.error("The list return empty, there were not product in stock, error EntityNotFoundException");
            throw new EntityNotFoundException("There are not products in stock");
        }
        return StockMapper.toListDTO(products);
    }

    public Product findProductById(Long id) {

        Product product = repo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found by this id " + id)
        );
        log.info("Search a product by id: " + product);
        return product;
    }

    public Product findProductByName(String name) {

        Product product = repo.findProductByName(name.toUpperCase());
        if(product == null){
            throw new EntityNotFoundException("Product not found by this name " + name);
        }
        log.info("Search a product by name: " + product);
        return product;
    }

    public ProductResponseDTO updateProductInStock(ProductCreateDTO update, Long id) {
        Product product = findProductById(id);
        if(repo.findProductByName(update.getName().toUpperCase()) != null && !(update.getName().equalsIgnoreCase(product.getName()))) {
            log.error("Error, this product was already found in stock");
            throw new DataUniqueViolationException("There is already a product registered with that name");
        }
        product.setName(update.getName().toUpperCase());
        product.setQuantity(update.getQuantity());
        if(product.getQuantity() <= 0) {
            log.error("Error quantity below zero in update a product, quantity can't be below zero, error ErrorQuantityBelowZero");
            throw new ErrorQuantityBelowZero("The product cannot have a quantity less than zero");
        }
        log.info("Updating a product by id: " + product);
        product = repo.save(product);
        return StockMapper.toDto(product);
    }

    public ProductResponseDTO removeQuantityByName(Integer quantity, String name) {
        Product product = findProductByName(name);
        product.setQuantity(product.getQuantity() - quantity);
        if(product.getQuantity() < 0) {
            throw new ErrorQuantityBelowZero("The quantity is not below zero");
        }
        log.info("Remove " + quantity + " of product by name " + name);
        product = repo.save(product);
        return StockMapper.toDto(product);
    }

    public ProductResponseDTO addQuantityByName(Integer quantity, String name) {
        Product product = findProductByName(name);
        product.setQuantity(product.getQuantity() + quantity);
        log.info("add " + quantity + " of product by name " + name);
        product = repo.save(product);
        return StockMapper.toDto(product);
    }

    public void deleteById(Long id) {
        findProductById(id);
        log.info("deleting a product by id: " + id);
        repo.deleteById(id);
    }
}
