package com.compass.ms_stock.web.controller;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.exceptions.handler.ErrorMessage;
import com.compass.ms_stock.services.StockService;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
@Validated
public class StockController {

    private final StockService stockService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO create) {
        ProductResponseDTO response = stockService.createProductInStock(create);
        response.add(linkTo(methodOn(StockController.class).findProductById(response.getId())).withRel("find your product by id"));
        response.add(linkTo(methodOn(StockController.class).findProductByName(response.getName())).withRel("find your product by name"));
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
        response.add(linkTo(methodOn(StockController.class).addQuantity(response.getQuantity(), response.getName())).withRel("Add quantity by Name"));
        response.add(linkTo(methodOn(StockController.class).findProductByName(response.getName())).withRel("find your product by name"));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/quantity/add/{quantity}/{name}")
    public ResponseEntity<ProductResponseDTO> addQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        ProductResponseDTO response = stockService.addQuantityByName(quantity, name);
        response.add(linkTo(methodOn(StockController.class).removeQuantity(response.getQuantity(), response.getName())).withRel("Remove quantity by Name"));
        response.add(linkTo(methodOn(StockController.class).findProductByName(response.getName())).withRel("find your product by name"));
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        stockService.deleteById(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
