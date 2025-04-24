package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Settings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettingsRepository extends JpaRepository<Settings, Long> {

    Settings findFirstByOrderByIdAsc();
}
