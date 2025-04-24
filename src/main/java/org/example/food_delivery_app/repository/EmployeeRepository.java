package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
