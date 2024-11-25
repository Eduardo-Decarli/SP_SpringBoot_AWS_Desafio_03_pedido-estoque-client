package com.compass.ms_stock.web.controller.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductResponseDTO {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer quantity;

    @Override
    public String toString() {
        return "ProductCreateDTO{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
