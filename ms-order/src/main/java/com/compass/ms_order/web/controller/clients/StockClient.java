package com.compass.ms_order.web.controller.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "stock-services", url = "http://localhost:8080/api/v1/stock")
public interface StockClient {

    @GetMapping("/quantity/remove/{quantity}/{name}")
    ResponseEntity<String> removeQuantity(@PathVariable Integer quantity, @PathVariable String name);

    @GetMapping("/product/name/{name}")
    ResponseEntity<String> findProductByName(@PathVariable String name);

}
