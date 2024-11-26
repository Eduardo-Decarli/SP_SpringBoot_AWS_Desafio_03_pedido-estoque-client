package com.compass.ms_order.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(name = "email_clients")
    private String clientEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", clientEmail='" + clientEmail + '\'' +
                ", products=" + products +
                '}';
    }
}
