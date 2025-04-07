package org.example.food_delivery_app.model;

import lombok.Data;

@Data
public class ProductRequest {

    private String name;
    private String description;
    private Category category;
    private double price;
    private Restaurant restaurant_id;
}
