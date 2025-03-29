package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap43.fooddelivery.model.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
}
