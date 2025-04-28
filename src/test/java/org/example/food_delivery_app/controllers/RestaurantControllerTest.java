package org.example.food_delivery_app.controllers;

import org.example.food_delivery_app.model.Restaurant;
import org.example.food_delivery_app.service.RestaurantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantControllerTest {

    @Mock
    private RestaurantService restaurantService;

    @InjectMocks
    private RestaurantController restaurantController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createRestaurant_shouldReturnCreatedRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Test Restaurant");
        restaurant.setAddress("Test Address");
        restaurant.setPhoneNumber("123456789");

        when(restaurantService.save(any(Restaurant.class))).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.createRestaurant(restaurant);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Test Restaurant", response.getBody().getName());
    }

    @Test
    void getAllRestaurants_shouldReturnListOfRestaurants() {
        Restaurant r1 = new Restaurant();
        r1.setName("Restaurant 1");

        Restaurant r2 = new Restaurant();
        r2.setName("Restaurant 2");

        when(restaurantService.getAll()).thenReturn(Arrays.asList(r1, r2));

        ResponseEntity<List<Restaurant>> response = restaurantController.getAllRestaurants();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getRestaurantById_shouldReturnRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Test Restaurant");

        when(restaurantService.getById(1L)).thenReturn(restaurant);

        ResponseEntity<Restaurant> response = restaurantController.getRestaurantById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test Restaurant", response.getBody().getName());
    }

    @Test
    void deleteRestaurant_shouldReturnNoContent() {
        doNothing().when(restaurantService).deleteById(1L);

        ResponseEntity<Void> response = restaurantController.deleteRestaurant(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(restaurantService, times(1)).deleteById(1L);
    }

    @Test
    void updateRestaurant_shouldReturnUpdatedRestaurant() {
        Restaurant updatedRestaurant = new Restaurant();
        updatedRestaurant.setId(1L);
        updatedRestaurant.setName("Updated Name");
        updatedRestaurant.setAddress("Updated Address");
        updatedRestaurant.setPhoneNumber("987654321");

        when(restaurantService.updateRestaurant(eq(1L), any(Restaurant.class))).thenReturn(updatedRestaurant);

        ResponseEntity<Restaurant> response = restaurantController.updateRestaurant(1L, updatedRestaurant);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Name", response.getBody().getName());
        assertEquals("Updated Address", response.getBody().getAddress());
        assertEquals("987654321", response.getBody().getPhoneNumber());
    }
}
