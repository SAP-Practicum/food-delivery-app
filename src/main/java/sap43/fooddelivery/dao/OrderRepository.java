package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap43.fooddelivery.model.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}