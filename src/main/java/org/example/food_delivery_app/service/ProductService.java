package org.example.food_delivery_app.service;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Category;
import org.example.food_delivery_app.model.Product;
import org.example.food_delivery_app.repository.ProductRepository;
import org.example.food_delivery_app.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantRepository restaurantRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id,Product productDetails){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setPrice(productDetails.getPrice());
        product.setCategory(productDetails.getCategory());

        return productRepository.save(product);
    }
}
