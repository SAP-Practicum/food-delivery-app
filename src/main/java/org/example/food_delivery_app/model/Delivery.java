package org.example.food_delivery_app.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "deliveries")
@Data
public class Delivery extends User {

    @OneToMany(mappedBy = "delivery")
    @JsonManagedReference("deliveries-orders")
    List<Order> orders = new ArrayList<>();

}
