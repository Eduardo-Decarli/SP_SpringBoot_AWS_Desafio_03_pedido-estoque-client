package com.compass.ms_order.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.List;
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class OrderResponseDTO extends RepresentationModel<OrderResponseDTO> implements Serializable {

    private Long id;

    @NotBlank(message = "the protocol can't be null")
    private String protocol;

    @Email
    @NotBlank(message = "the email address can't be null")
    private String clientEmail;

    @NotNull(message = "the products can't be null")
    private List<ProductResponseDTO> products;
}
