package com.compass.ms_order.services;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import com.compass.ms_order.exeptions.EntityNotFoundException;
import com.compass.ms_order.repositories.OrderRepository;
import com.compass.ms_order.web.controller.clients.StockClient;
import com.compass.ms_order.web.controller.clients.UserClient;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.mapper.OrderMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class OrderServices {

    private final StockClient stockClient;
    private final UserClient userClient;
    private final OrderRepository repo;

    public OrderResponseDTO createOrder(OrderCreateDTO createDTO) {
            Order create = OrderMapper.toOrder(createDTO);
            userClient.consultEmailUser(create.getClientEmail());

            for (Product correntProduct : create.getProducts()) {
                stockClient.findProductByName(correntProduct.getName());
                stockClient.removeQuantity(correntProduct.getQuantity(), correntProduct.getName());
                correntProduct.setOrder(create);
            }
            create = repo.save(create);
            return OrderMapper.toDto(create);
    }

    public OrderResponseDTO updateOrderById(OrderCreateDTO updateDTO, Long id) {
        Order update = OrderMapper.toOrder(updateDTO);
        Order currentOrder = findOrderById(id);
        userClient.consultEmailUser(update.getClientEmail());
        currentOrder.setClientEmail(update.getClientEmail());
        List<Product> updatedProducts = new ArrayList<>();

        for (Product newProduct : update.getProducts()) {
            boolean productExists = false;
            stockClient.findProductByName(newProduct.getName());
            for (Product existingProduct : currentOrder.getProducts()) {
                if (existingProduct.getName().equalsIgnoreCase(newProduct.getName())) {
                    productExists = true;

                    stockClient.addQuantity(existingProduct.getQuantity(), existingProduct.getName());
                    stockClient.removeQuantity(newProduct.getQuantity(), newProduct.getName());

                    existingProduct.setQuantity(newProduct.getQuantity());
                    updatedProducts.add(existingProduct);
                    break;
                }
            }

            if (!productExists) {
                stockClient.removeQuantity(newProduct.getQuantity(), newProduct.getName());
                newProduct.setOrder(currentOrder);
                updatedProducts.add(newProduct);
            }
        }

        currentOrder.setProducts(updatedProducts);
        update = repo.save(currentOrder);
        return OrderMapper.toDto(update);
    }

    public List<OrderResponseDTO> findAllOrderByEmail(String email) {
        userClient.consultEmailUser(email);
        List<Order> orders = repo.findOrderByClientEmail(email);
        return OrderMapper.toListDTO(orders);
    }

    public Order findOrderById(Long id) {
        Order order = repo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order not found by Id")
        );
        return order;
    }


}
