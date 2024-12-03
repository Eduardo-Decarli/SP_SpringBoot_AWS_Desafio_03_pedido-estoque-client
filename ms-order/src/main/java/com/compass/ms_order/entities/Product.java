package com.compass.ms_order.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "product")
public class Product implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonIgnore
    private Order order;

    @Column
    private String name;

    @Column
    private Integer quantity;
}
