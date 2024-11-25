package com.compass.ms_stock.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Stock implements Serializable {

    @ManyToOne
    private List<Product> products;

    @Override
    public String toString() {
        return "Stock{" +
                "products=" + products +
                '}';
    }
}
