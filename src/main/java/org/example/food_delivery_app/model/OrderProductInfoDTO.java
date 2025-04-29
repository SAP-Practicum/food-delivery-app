package org.example.food_delivery_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderProductInfoDTO {
    private String productName;
    private double pricePerUnit;
    private int quantity;
    private double totalProductPrice;
}
