package com.compass.ms_stock.web.controller;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.services.StockService;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockDTO;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> createProduct(@Valid @RequestBody ProductCreateDTO create) {
        stockService.createProductInStock(create);
        return ResponseEntity.status(HttpStatus.CREATED).body("Sucessful create product");
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        List<ProductResponseDTO> products = stockService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        Product product = stockService.findProductById(id);
        return ResponseEntity.ok(StockDTO.toDto(product));
    }

    @GetMapping("/quantity/remove/{quantity}/{id}")
    public ResponseEntity<String> removeQuantity(@PathVariable Integer quantity, @PathVariable Long id) {
        stockService.removeQuantityById(quantity, id);
        return ResponseEntity.ok().body("Removed quantity Sucessful");
    }

    @GetMapping("/quantity/add/{quantity}/{id}")
    public ResponseEntity<String> addQuantity(@PathVariable Integer quantity, @PathVariable Long id) {
        stockService.addQuantityById(quantity, id);
        return ResponseEntity.ok().body("add quantity Sucessful");
    }
}
