package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
