package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}