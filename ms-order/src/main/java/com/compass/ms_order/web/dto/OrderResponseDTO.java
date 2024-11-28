package com.compass.ms_order.web.dto;

import com.compass.ms_order.entities.Product;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class OrderResponseDTO {

    private Long id;

    @Email
    @NotBlank(message = "the email address can't be null")
    private String clientEmail;

    @NotNull(message = "the products can't be null")
    private List<Product> products;
}
