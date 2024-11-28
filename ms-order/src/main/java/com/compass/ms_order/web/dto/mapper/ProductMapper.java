package com.compass.ms_order.web.dto.mapper;

import com.compass.ms_order.entities.Product;
import com.compass.ms_order.web.dto.ProductCreateDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static Product toClient(ProductCreateDTO createDTO) {
        return new ModelMapper().map(createDTO, Product.class);
    }

    public static ProductResponseDTO toDto(Product product) {
        return new ModelMapper().map(product, ProductResponseDTO.class);
    }

    public static List<ProductResponseDTO> toListDTO(List<Product> products) {
        return products.stream().map(product -> toDto(product)).collect(Collectors.toList());
    }
}
