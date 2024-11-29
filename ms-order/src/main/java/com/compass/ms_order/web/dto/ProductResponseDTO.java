package com.compass.ms_order.web.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ProductResponseDTO {

    @NotBlank(message = "the name can't be null")
    private String name;

    @NotNull(message = "the quantity can't be null")
    private Integer quantity;
}
