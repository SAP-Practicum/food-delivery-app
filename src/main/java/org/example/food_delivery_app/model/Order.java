package org.example.food_delivery_app.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="customer_id",nullable = false)
    @JsonBackReference("customer-orders")
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "delivery_id")
    @JsonBackReference("deliveries-orders")
    private Delivery delivery;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();

    @NotBlank
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderStatus status;

    @NotBlank
    private LocalDateTime createdDate;
}
