package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sap43.fooddelivery.model.Supplier;
@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
