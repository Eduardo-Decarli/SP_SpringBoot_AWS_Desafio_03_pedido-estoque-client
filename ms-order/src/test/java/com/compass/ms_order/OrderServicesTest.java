package com.compass.ms_order;

import com.compass.ms_order.entities.Order;
import com.compass.ms_order.entities.Product;
import com.compass.ms_order.exeptions.EntityNotFoundException;
import com.compass.ms_order.repositories.OrderRepository;
import com.compass.ms_order.services.OrderServices;
import com.compass.ms_order.web.controller.clients.StockClient;
import com.compass.ms_order.web.controller.clients.UserClient;
import com.compass.ms_order.web.dto.OrderCreateDTO;
import com.compass.ms_order.web.dto.OrderResponseDTO;
import com.compass.ms_order.web.dto.ProductCreateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServicesTest {

    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderServices services;

    @Mock
    private UserClient userClient;

    @Mock
    private StockClient stockClient;

    @Test
    @DisplayName("Should create a Order with successfully")
    void shouldCreateAOrder() {
        List<ProductCreateDTO> productList = List.of(new ProductCreateDTO("Computer", 1));
        Order order = new Order(1L, "rodrigo@example.com", List.of(new Product(null, null, "Computer", 1)));
        OrderCreateDTO createDTO = new OrderCreateDTO(order.getClientEmail(), productList);
        when(repository.save(any(Order.class))).thenReturn(order);

        OrderResponseDTO response = services.createOrder(createDTO);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("rodrigo@example.com", response.getClientEmail());
        assertEquals("Computer", response.getProducts().getFirst().getName());
    }

    @Test
    @DisplayName("Should throw Exception when data not found")
    void createOrderCase2() {
        List<ProductCreateDTO> productList = List.of(new ProductCreateDTO("Computer", 1));
        Order order = new Order(1L, "rodrigo@example.com", List.of(new Product(null, null, "Computer", 1)));
        OrderCreateDTO createDTO = new OrderCreateDTO(order.getClientEmail(), productList);

        when(userClient.consultEmailUser(any())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> services.createOrder(createDTO));
    }

    @Test
    @DisplayName("Should update a Order with successfully")
    void shouldUpdateOrderCase1() {
        List<ProductCreateDTO> productList = List.of(new ProductCreateDTO("Computer", 1));
        Order existingOrder = new Order(1L, "rodrigo@example.com", List.of(new Product(1L, null, "Computer", 1)));
        Order updatedOrder = new Order(1L, "Carlos@example.com", List.of(new Product(1L, null, "Computer", 1)));
        OrderCreateDTO createDTO = new OrderCreateDTO("Carlos@example.com", productList);

        when(repository.save(any(Order.class))).thenReturn(updatedOrder);
        when(repository.findById(any())).thenReturn(Optional.of(existingOrder));

        OrderResponseDTO response = services.updateOrderById(createDTO, 1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Carlos@example.com", response.getClientEmail());
        assertEquals("Computer", response.getProducts().getFirst().getName());
    }

    @Test
    @DisplayName("Should return a Order by id with successfully")
    void findOrderByIdCase1() {
        List<Product> productList = List.of(new Product(1L, null, "Computer", 1));
        Order existingProduct = new Order(1L, "rodrigo@example.com", productList);

        when(repository.findById(any(Long.class))).thenReturn(Optional.of(existingProduct));

        Order response = services.findOrderById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("rodrigo@example.com", response.getClientEmail());
        assertEquals("Computer", response.getProducts().getFirst().getName());
    }

    @Test
    @DisplayName("Should throw Exception when data not found")
    void findOrderByIdCase2() {

        when(repository.findById(any(Long.class))).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> services.findOrderById(1L));
    }

    @Test
    void findProductById() {
    }
}