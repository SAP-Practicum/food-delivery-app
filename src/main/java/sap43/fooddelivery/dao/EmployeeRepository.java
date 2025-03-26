package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
