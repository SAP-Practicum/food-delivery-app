package sap43.fooddelivery.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.Order;
import lombok.*;
import sap43.fooddelivery.model.User;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User {
    @OneToMany(mappedBy = "customer")
    private List<Order> orders;
}
