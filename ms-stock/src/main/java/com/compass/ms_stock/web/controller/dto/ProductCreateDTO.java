package com.compass.ms_stock.web.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class ProductCreateDTO {


    @NotBlank(message = "The product's name can't be null")
    private String name;

    @NotNull(message = "The product's quantity can't be null")
    private Integer quantity;

}
