package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
