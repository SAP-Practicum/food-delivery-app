package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
