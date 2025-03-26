package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}