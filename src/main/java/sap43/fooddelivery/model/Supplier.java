package sap43.fooddelivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
public class Supplier extends User {
    @OneToMany(mappedBy = "supplier")
    private List<Order> orders;

    private double totalEarnings;
}
