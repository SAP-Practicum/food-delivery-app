package org.example.food_delivery_app.service;

import org.example.food_delivery_app.model.Category;
import org.example.food_delivery_app.model.Product;
import org.example.food_delivery_app.repository.ProductRepository;
import org.example.food_delivery_app.repository.RestaurantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    private ProductRepository productRepository;
    private RestaurantRepository restaurantRepository;
    private ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        restaurantRepository = mock(RestaurantRepository.class);
        productService = new ProductService(productRepository, restaurantRepository);
    }

    @Test
    // Проверяваме запазването на продукт
    void testSaveProduct() {
        Product product = new Product();
        product.setName("Burger");

        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.save(product);

        assertEquals("Burger", saved.getName());
        verify(productRepository).save(product);
    }

    @Test
    // Проверяваме дали се връщат всички запазени продукти
    void testGetAllProducts() {
        Product p1 = new Product(); p1.setName("Burger");
        Product p2 = new Product(); p2.setName("Fries");

        when(productRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Product> products = productService.getAll();

        assertEquals(2, products.size());
        verify(productRepository).findAll();
    }

    @Test
    // Проверяваме продукти по категория
    void testGetByCategory() {
        Category category = Category.PIZZA;
        Product p = new Product(); p.setCategory(category);

        when(productRepository.findByCategory(category)).thenReturn(List.of(p));

        List<Product> products = productService.getByCategory(category);

        assertEquals(1, products.size());
        assertEquals(category, products.get(0).getCategory());
        verify(productRepository).findByCategory(category);
    }

    @Test
    // Проверяваме изтриването по id
    void testDeleteById() {
        Long id = 1L;
        productService.deleteById(id);
        verify(productRepository).deleteById(id);
    }

    @Test
    // Проверяваме актуализирането на продукт
    void testUpdateProduct() {
        Long id = 1L;

        Product existing = new Product();
        existing.setId(id);
        existing.setName("Old Burger");

        Product updatedDetails = new Product();
        updatedDetails.setName("New Burger");
        updatedDetails.setDescription("Juicy");
        updatedDetails.setPrice(10.0);
        updatedDetails.setCategory(Category.BURGER);

        when(productRepository.findById(id)).thenReturn(Optional.of(existing));
        when(productRepository.save(any(Product.class))).thenReturn(existing);

        Product result = productService.updateProduct(id, updatedDetails);

        assertEquals("New Burger", result.getName());
        assertEquals("Juicy", result.getDescription());
        assertEquals(10.0, result.getPrice());
        assertEquals(Category.BURGER, result.getCategory());
        verify(productRepository).save(existing);
    }

    @Test
    // Проверяваме актуализирането на продукт ако не бъде намерен такъв
    void testUpdateProductThrowsIfNotFound() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            productService.updateProduct(id, new Product())
        );

        assertEquals("Product not found", exception.getMessage());
    }
}
