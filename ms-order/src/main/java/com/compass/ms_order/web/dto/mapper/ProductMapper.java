package com.compass.ms_order.web.dto.mapper;

import com.compass.ms_order.entities.Product;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import org.modelmapper.ModelMapper;

public class ProductMapper {

    public static ProductResponseDTO toDto(Product product) {
        return new ModelMapper().map(product, ProductResponseDTO.class);
    }
}
