package org.example.food_delivery_app.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    @PositiveOrZero
    private double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne
    @JoinColumn(name="restaurant_id",nullable = false)
    @JsonBackReference(value = "restaurant-products")
    private Restaurant restaurant;

    @ManyToMany(mappedBy = "products")
    @JsonBackReference(value = "order-product")
    private List<Order> orders = new ArrayList<>();

    @JsonProperty("restaurantId")
    public Long getRestaurantId() {
        return restaurant != null ? restaurant.getId() : null;
    }
}
