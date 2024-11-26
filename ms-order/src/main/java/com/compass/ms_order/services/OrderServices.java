package com.compass.ms_order.services;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import com.compass.ms_order.repositories.OrderRepository;
import com.compass.ms_order.web.controller.clients.StockClient;
import com.compass.ms_order.web.controller.clients.UserClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderServices {

    private final StockClient stockClient;
    private final UserClient userClient;
    private final OrderRepository repository;

    public Order createOrder(Order create) {
        userClient.consultEmailUser(create.getClientEmail());
        for(Product x : create.getProducts()) {
            stockClient.findProductByName(x.getName());
            stockClient.removeQuantity(x.getQuantity(), x.getName());
            x.setOrder(create);
        }
        repository.save(create);
        return create;
    }

    public List<Order> findAllOrderByEmail(String email) {
        userClient.consultEmailUser(email);
        List<Order> order = repository.findOrderByClientEmail(email);
        return order;
    }
}
