package com.compass.ms_stock.web.controller;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.services.StockService;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import com.compass.ms_stock.web.controller.dto.mapper.StockDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@AllArgsConstructor
@Tag(name = "Stock API", description = "API for managing product stock")
public class StockController {

    private final StockService stockService;

    @Operation(summary = "Create a new product", description = "Add a new product to the stock")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid product data")
    })
    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductCreateDTO create) {
        stockService.createProductInStock(create);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully create product");
    }

    @Operation(summary = "Get all products", description = "Return a list of all products in the stock")
    @ApiResponse(responseCode = "200", description = "List all products successfully")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> findAllProducts() {
        List<ProductResponseDTO> products = stockService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Get product by ID", description = "Retrieves a product by ID")
    @Parameter(name = "id", description = "ID of the product", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/id/{id}")
    public ResponseEntity<ProductResponseDTO> findProductById(@PathVariable Long id) {
        Product product = stockService.findProductById(id);
        return ResponseEntity.ok(StockDTO.toDto(product));
    }

    @Operation(summary = "find product by name", description = "Retrieves a product by name")
    @Parameter(name = "name", description = "Name of the product", required = true)
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/product/name/{name}")
    public ResponseEntity<ProductResponseDTO> findProductByName(@PathVariable String name) {
        Product product = stockService.findProductByName(name);
        return ResponseEntity.ok(StockDTO.toDto(product));
    }

    @Operation(summary = "Update a product", description = "Updates the details of an existing product")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@RequestBody ProductCreateDTO update, @PathVariable Long id) {
        stockService.updateProductInStock(update, id);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully update product");
    }

    @Operation(summary = "Remove product quantity", description = "Removes a specified quantity from a product in stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity removed successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/quantity/remove/{quantity}/{name}")
    public ResponseEntity<String> removeQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        stockService.removeQuantityByName(quantity, name);
        return ResponseEntity.ok().body("Removed quantity successfully");
    }

    @Operation(summary = "Add product quantity", description = "Add a specified quantity to a product in stock")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Quantity added successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @GetMapping("/quantity/add/{quantity}/{name}")
    public ResponseEntity<String> addQuantity(@PathVariable Integer quantity, @PathVariable String name) {
        stockService.addQuantityByName(quantity, name);
        return ResponseEntity.ok().body("add quantity successfully");
    }
}
