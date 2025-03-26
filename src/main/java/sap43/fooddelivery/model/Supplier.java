package sap43.fooddelivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends User {
    @OneToMany(mappedBy = "supplier")
    private List<Delivery> deliveries;

    private double totalEarnings;
}
