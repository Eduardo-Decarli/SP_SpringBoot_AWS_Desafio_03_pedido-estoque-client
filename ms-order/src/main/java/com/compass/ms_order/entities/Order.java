package com.compass.ms_order.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String protocol;

    @Email
    @Column(name = "email_clients")
    private String clientEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Product> products;
}
