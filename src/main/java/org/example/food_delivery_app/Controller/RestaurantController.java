package org.example.food_delivery_app.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Restaurant;
import org.example.food_delivery_app.service.RestaurantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant){
        return ResponseEntity.ok(restaurantService.save(restaurant));
    }


    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants(){
        return ResponseEntity.ok(restaurantService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id){
        return ResponseEntity.ok(restaurantService.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Long id){
        restaurantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(
            @PathVariable Long id,
            @Valid @RequestBody Restaurant restaurantDetails){

        Restaurant updatedRestaurant = restaurantService.updateRestaurant(id,restaurantDetails);
        return ResponseEntity.ok(updatedRestaurant);
    }
}
