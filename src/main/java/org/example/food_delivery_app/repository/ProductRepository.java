package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Category;
import org.example.food_delivery_app.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
