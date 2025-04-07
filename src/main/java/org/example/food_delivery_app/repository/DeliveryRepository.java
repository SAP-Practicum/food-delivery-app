package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
