package sap43.fooddelivery.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sap43.fooddelivery.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
