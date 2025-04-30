package org.example.food_delivery_app.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Category;
import org.example.food_delivery_app.model.Product;
import org.example.food_delivery_app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(productService.getAll());
    }

    @GetMapping("/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable Category category){

        return ResponseEntity.ok(productService.getByCategory(category));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product productDetails){
        Product updatedProduct = productService.updateProduct(id,productDetails);

        return ResponseEntity.ok(updatedProduct);
    }
}
