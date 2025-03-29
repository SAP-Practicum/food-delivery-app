package sap43.fooddelivery.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Data
@DiscriminatorValue("CUSTOMER")
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}