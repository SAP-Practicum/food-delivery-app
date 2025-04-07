package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByName(String name);
}
