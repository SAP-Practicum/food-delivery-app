package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
