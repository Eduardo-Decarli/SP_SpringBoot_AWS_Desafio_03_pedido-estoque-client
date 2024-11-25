package com.compass.ms_stock.web.controller.dto.mapper;

import com.compass.ms_stock.entities.Product;
import com.compass.ms_stock.web.controller.dto.ProductCreateDTO;
import com.compass.ms_stock.web.controller.dto.ProductResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class StockDTO {

    public static Product toProduct(ProductCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Product.class);
    }

    public static ProductResponseDTO toDto(Product client) {
        return new ModelMapper().map(client, ProductResponseDTO.class);
    }

    public static List<ProductResponseDTO> toListDTO(List<Product> clients) {
        return clients.stream().map(client -> toDto(client)).collect(Collectors.toList());
    }
}
