package com.compass.ms_stock.web.controller;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.services.StockService;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateDTO create) {
        stockService.createProductInStock(create);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucessfully create product");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        List<ProductResponseDTO> products = stockService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/id/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        Product product = stockService.findProductById(id);
        return ResponseEntity.ok(StockDTO.toDto(product));
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<ProductResponseDTO> findProductByName(@PathVariable String name) {
        Product product = stockService.findProductByName(name);
        return ResponseEntity.ok(StockDTO.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductCreateDTO update, @PathVariable Long id) {
        stockService.updateProductInStock(update, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucessfully update product");
    }

    @GetMapping("/quantity/remove/{quantity}/{name}")
    public ResponseEntity<String> removeQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        stockService.removeQuantityByName(quantity, name);
        return ResponseEntity.ok().body("Removed quantity Sucessfully");
    }

    @GetMapping("/quantity/add/{quantity}/{name}")
    public ResponseEntity<String> addQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        stockService.addQuantityByName(quantity, name);
        return ResponseEntity.ok().body("add quantity Sucessfully");
    }
}
