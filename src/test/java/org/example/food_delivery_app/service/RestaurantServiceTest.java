package org.example.food_delivery_app.service;

import org.example.food_delivery_app.model.Restaurant;
import org.example.food_delivery_app.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RestaurantServiceTest {

    private RestaurantRepository restaurantRepository;
    private RestaurantService restaurantService;

    @BeforeEach
    void setUp() {
        restaurantRepository = mock(RestaurantRepository.class);
        restaurantService = new RestaurantService(restaurantRepository);
    }

    @Test
    void testSaveRestaurant() {
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Sushi Palace");

        when(restaurantRepository.save(restaurant)).thenReturn(restaurant);

        Restaurant saved = restaurantService.save(restaurant);

        assertEquals("Sushi Palace", saved.getName());
        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void testGetAllRestaurants() {
        Restaurant r1 = new Restaurant(); r1.setName("R1");
        Restaurant r2 = new Restaurant(); r2.setName("R2");

        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(r1, r2));

        List<Restaurant> all = restaurantService.getAll();

        assertEquals(2, all.size());
        verify(restaurantRepository).findAll();
    }

    @Test
    void testGetById() {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(1L);
        restaurant.setName("Tacos");

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

        Restaurant found = restaurantService.getById(1L);

        assertEquals("Tacos", found.getName());
    }

    @Test
    void testGetByIdThrowsException() {
        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            restaurantService.getById(1L)
        );

        assertTrue(exception.getMessage().contains("Restaurant not found"));
    }

    @Test
    void testDeleteById() {
        restaurantService.deleteById(5L);
        verify(restaurantRepository).deleteById(5L);
    }

    @Test
    void testUpdateRestaurant() {
        Long id = 1L;

        Restaurant existing = new Restaurant();
        existing.setId(id);
        existing.setName("Old");
        existing.setAddress("Old Address");
        existing.setPhoneNumber("000");

        Restaurant updates = new Restaurant();
        updates.setName("New Name");
        updates.setAddress("New Address");
        updates.setPhoneNumber("123");

        when(restaurantRepository.findById(id)).thenReturn(Optional.of(existing));
        when(restaurantRepository.save(existing)).thenReturn(existing);

        Restaurant updated = restaurantService.updateRestaurant(id, updates);

        assertEquals("New Name", updated.getName());
        assertEquals("New Address", updated.getAddress());
        assertEquals("123", updated.getPhoneNumber());
        verify(restaurantRepository).save(existing);
    }

    @Test
    void testUpdateThrowsIfNotFound() {
        when(restaurantRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            restaurantService.updateRestaurant(99L, new Restaurant())
        );

        assertTrue(exception.getMessage().contains("Restaurant not found"));
    }
}
