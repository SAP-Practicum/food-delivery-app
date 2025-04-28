package org.example.food_delivery_app.controllers;

import org.example.food_delivery_app.model.Category;
import org.example.food_delivery_app.model.Product;
import org.example.food_delivery_app.service.ProductService;
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

class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createProduct_shouldReturnCreatedProduct() {
        Product product = new Product();
        product.setName("Pizza Margherita");
        product.setDescription("Delicious pizza");
        product.setPrice(10.0);
        product.setCategory(Category.PIZZA);

        when(productService.save(any(Product.class))).thenReturn(product);

        ResponseEntity<Product> response = productController.createProduct(product);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Pizza Margherita", response.getBody().getName());
    }

    @Test
    void getAllProducts_shouldReturnListOfProducts() {
        Product p1 = new Product();
        p1.setName("Pizza");

        Product p2 = new Product();
        p2.setName("Burger");

        when(productService.getAll()).thenReturn(Arrays.asList(p1, p2));

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getProductByCategory_shouldReturnProductsOfCategory() {
        Product p1 = new Product();
        p1.setName("Pizza");
        p1.setCategory(Category.PIZZA);

        when(productService.getByCategory(Category.PIZZA)).thenReturn(Arrays.asList(p1));

        ResponseEntity<List<Product>> response = productController.getProductByCategory(Category.PIZZA);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(Category.PIZZA, response.getBody().get(0).getCategory());
    }

    @Test
    void deleteProduct_shouldReturnNoContent() {
        doNothing().when(productService).deleteById(1L);

        ResponseEntity<Void> response = productController.deleteProduct(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(productService, times(1)).deleteById(1L);
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setId(1L);
        updatedProduct.setName("Updated Pizza");
        updatedProduct.setDescription("Even more delicious");
        updatedProduct.setPrice(12.0);
        updatedProduct.setCategory(Category.PIZZA);

        when(productService.updateProduct(eq(1L), any(Product.class))).thenReturn(updatedProduct);

        ResponseEntity<Product> response = productController.updateProduct(1L, updatedProduct);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Updated Pizza", response.getBody().getName());
        assertEquals(12.0, response.getBody().getPrice());
    }
}
