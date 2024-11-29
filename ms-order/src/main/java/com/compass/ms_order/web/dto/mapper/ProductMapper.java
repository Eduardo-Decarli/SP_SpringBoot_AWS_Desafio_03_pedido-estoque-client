package com.compass.ms_order.web.dto.mapper;

import com.compass.ms_order.entities.Product;
import com.compass.ms_order.web.dto.ProductCreateDTO;
import com.compass.ms_order.web.dto.ProductResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponseDTO toDto(Product product) {
        return new ModelMapper().map(product, ProductResponseDTO.class);
    }
}
