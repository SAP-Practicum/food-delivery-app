package org.example.food_delivery_app.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderSummaryDTO {
    private List<OrderProductInfoDTO> products;
    private double totalPrice;
    private OrderStatus orderStatus;
}
