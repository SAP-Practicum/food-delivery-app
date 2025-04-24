package org.example.food_delivery_app.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "settings")
public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double bonusThreshold = 1500.0;

    @Column(nullable = false)
    private double bonusPercentage = 10.0;
}
