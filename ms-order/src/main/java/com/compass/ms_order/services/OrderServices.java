package com.compass.ms_order.services;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import com.compass.ms_order.exeptions.EntityNotFoundException;
import com.compass.ms_order.repositories.OrderFunctionsRepository;
import com.compass.ms_order.repositories.OrderRepository;
import com.compass.ms_order.repositories.ProductRepository;
import com.compass.ms_order.web.controller.clients.StockClient;
import com.compass.ms_order.web.controller.clients.UserClient;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import com.compass.ms_order.web.dto.mapper.OrderMapper;
import com.compass.ms_order.web.dto.mapper.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
@Log4j2
public class OrderServices implements OrderFunctionsRepository {

    private final StockClient stockClient;
    private final UserClient userClient;
    private final OrderRepository repo;
    private final ProductRepository productRepository;

    public OrderResponseDTO createOrder(OrderCreateDTO createDTO) {
            Order create = OrderMapper.toOrder(createDTO);
            userClient.consultEmailUser(create.getClientEmail());

            for (Product correntProduct : create.getProducts()) {
                stockClient.findProductByName(correntProduct.getName());
                stockClient.removeQuantity(correntProduct.getQuantity(), correntProduct.getName());
                correntProduct.setOrder(create);
            }

            create.setClientEmail(create.getClientEmail().toLowerCase());
            create.setProtocol(UUID.randomUUID().toString());
            create = repo.save(create);
            log.info("Creating a new Order: " + create);
            return OrderMapper.toDto(create);
    }

    @Transactional
    public OrderResponseDTO updateOrderById(OrderCreateDTO updateDTO, Long id) {
        Order currentOrder = findOrderById(id);
        userClient.consultEmailUser(updateDTO.getClientEmail());
        currentOrder.setClientEmail(updateDTO.getClientEmail().toLowerCase());

        for (Product product : currentOrder.getProducts()) {
            stockClient.addQuantity(product.getQuantity(), product.getName());
        }

        List<Product> updatedProducts = new ArrayList<>();
        for (Product newProduct : updateDTO.getProducts()) {
            stockClient.findProductByName(newProduct.getName());
            stockClient.removeQuantity(newProduct.getQuantity(), newProduct.getName());
            newProduct.setOrder(currentOrder);
            updatedProducts.add(newProduct);
        }
        currentOrder.setProducts(updatedProducts);
        currentOrder = repo.save(currentOrder);
        log.info("Order updated successfully: " + currentOrder);
        return OrderMapper.toDto(currentOrder);
    }


    public List<OrderResponseDTO> findAllOrdersByEmail(String email) {
        userClient.consultEmailUser(email);
        List<Order> orders = repo.findOrderByClientEmail(email.toLowerCase());
        log.info(orders);
        if(orders.isEmpty()) {
            throw new EntityNotFoundException("No requests were registered for the requested user");
        }
        log.info("Finding a order by email's client: " + email);
        return OrderMapper.toListDTO(orders);
    }

    public Order findOrderById(Long id) {
        Order order = repo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order not found by Id")
        );
        log.info("Finding a order by id: " + order);
        return order;
    }

    public ProductResponseDTO findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Product not found by Id")
        );
        log.info("Finding a product by id: " + product);
        return ProductMapper.toDto(product);
    }

    public OrderResponseDTO findOrderByProtocol(String protocol) {
        Order order = repo.findOrderByProtocol(protocol);
        if(order == null) {
            throw new EntityNotFoundException("Order by protocol not found");
        }
        log.info("Finding a order by protocol: " + order);
        return OrderMapper.toDto(order);
    }

    public void deleteOrderById(Long id) {
        findOrderById(id);
        log.info("Deleting a order by id: " + id);
        repo.deleteById(id);
    }
}
