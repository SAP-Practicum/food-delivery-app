package org.example.food_delivery_app.service;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Restaurant;
import org.example.food_delivery_app.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public Restaurant save(Restaurant restaurant){
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAll(){
        return restaurantRepository.findAll();
    }

    public Restaurant getById(Long id){
        return restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found "));
    }

    public void deleteById(Long id){
        restaurantRepository.deleteById(id);
    }

    public Restaurant updateRestaurant(Long id,Restaurant restaurantDetails){
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found "));

        restaurant.setName(restaurantDetails.getName());
        restaurant.setAddress(restaurantDetails.getAddress());
        restaurant.setPhoneNumber(restaurantDetails.getPhoneNumber());

        return restaurantRepository.save(restaurant);
    }
}
