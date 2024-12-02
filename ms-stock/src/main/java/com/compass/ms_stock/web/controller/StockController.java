package com.compass.ms_stock.web.controller;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.services.StockService;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
@Tag(name = "Stock API", description = "API for managing product stock")
@Validated
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO create) {
        ProductResponseDTO response = stockService.createProductInStock(create);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        List<ProductResponseDTO> products = stockService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/product/id/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        Product product = stockService.findProductById(id);
        return ResponseEntity.ok(StockMapper.toDto(product));
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<ProductResponseDTO> findProductByName(@PathVariable String name) {
        Product product = stockService.findProductByName(name);
        return ResponseEntity.ok(StockMapper.toDto(product));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@Valid @RequestBody ProductCreateDTO update, @PathVariable Long id) {
        ProductResponseDTO response = stockService.updateProductInStock(update, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/quantity/remove/{quantity}/{name}")
    public ResponseEntity<ProductResponseDTO> removeQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        ProductResponseDTO response = stockService.removeQuantityByName(quantity, name);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/quantity/add/{quantity}/{name}")
    public ResponseEntity<ProductResponseDTO> addQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        ProductResponseDTO response = stockService.addQuantityByName(quantity, name);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        stockService.deleteById(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
