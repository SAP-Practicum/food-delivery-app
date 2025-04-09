package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}
