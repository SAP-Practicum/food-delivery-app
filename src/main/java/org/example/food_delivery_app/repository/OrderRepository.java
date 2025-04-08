package org.example.food_delivery_app.repository;

import org.example.food_delivery_app.model.Order;
import org.example.food_delivery_app.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByStatus(OrderStatus status);
    List<Order> findByCustomerId(Long customerId);
    List<Order> findAllByDeliveryIdAndCreatedDateBetween(Long deliveryId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("""
select sum (p.price)
from Order o
join o.products p
join p.restaurant r
where r.id = :restaurantId
and o.createdDate between :startDate and :endDate
""")
    Double calculateRevenue(@Param("restaurantId") Long restaurantId,
                            @Param("startDate") LocalDateTime startDate,
                            @Param("endDate") LocalDateTime endDate);

    @Query("""
select o
from Order o
join o.products p
where p.restaurant.id = :restaurantId
and o.status = 'PENDING'
""")
    List<Order> findPendingOrdersByRestaurantId(@Param("restaurantId") long restaurantId);

}

