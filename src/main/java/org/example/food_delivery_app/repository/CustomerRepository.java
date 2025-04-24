package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
