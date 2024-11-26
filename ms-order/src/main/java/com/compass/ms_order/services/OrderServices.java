package com.compass.ms_order.services;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import com.compass.ms_order.web.controller.clients.StockClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OrderServices {

    private final StockClient stockClient;

    public Order createOrder(Order create) {
        for(Product x : create.getProducts()) {
            stockClient.removeQuantity(x.getQuantity(), 2L);
        }
        return create;
    }
}
