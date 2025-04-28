package org.example.food_delivery_app.Controller;

import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Order;
import org.example.food_delivery_app.model.OrderStatus;
import org.example.food_delivery_app.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/revenue")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Double> getRestaurantRevenue(
          @RequestParam Long restaurantId,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        Double revenue = orderService.getRestaurantRevenue(restaurantId, startDate, endDate);
        return ResponseEntity.ok(revenue);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long customerId,
            @RequestBody List<Long> productIds
    ){
        Order order = orderService.createOrder(customerId, productIds);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<List<Order>> getOrdersByCustomerId(@PathVariable Long customerId){
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getAllPendingOrders(){
        return ResponseEntity.ok(orderService.getAllPendingOrders());
    }

    @PostMapping("/accept/{orderId}")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<String> acceptOrder(
            @PathVariable Long orderId,
            @RequestParam Long deliveryId
    ){
        orderService.acceptOrder(orderId, deliveryId);
        return ResponseEntity.ok("Order accepted successfully by delivery: "+ deliveryId);
    }

    @PutMapping("/delivered/{orderId}")
    @PreAuthorize("hasRole('DELIVERY')")
    public ResponseEntity<String> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam Long deliveryId
    ){
        orderService.deliveredOrder(orderId);
        return ResponseEntity.ok("Order is delivered by delivery: "+ deliveryId);
    }

    @GetMapping("/track/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    private ResponseEntity<OrderStatus> trackOrder(
            @PathVariable Long orderId,
            @RequestParam Long customerId
    ){
        OrderStatus status = orderService.getOrderStatus(orderId, customerId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/earnings/{deliveryId}")
    @PreAuthorize("hasAnyRole('EMPLOYEE','DELIVERY')")
    public ResponseEntity<Double> getEarningsForDelivery(
            @PathVariable Long deliveryId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ){
        double earnings = orderService.getEarningsForDelivery(deliveryId, startDate, endDate);
        return ResponseEntity.ok(earnings);
    }

    @PostMapping("/preparing/{orderId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> prepareOrder(@PathVariable Long orderId){
        orderService.preparingOrder(orderId);
        return ResponseEntity.ok("Order prepared successfully");
    }

    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId){
        orderService.cancelOrder(orderId);
        return ResponseEntity.ok("Order cancelled successfully");
    }

    @GetMapping("/pending/{restaurantId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<Order>> getPendingOrdersByRestaurantId(@PathVariable Long restaurantId){
        List<Order> pendingOrders = orderService.getPendingOrdersByRestaurantId(restaurantId);
        return ResponseEntity.ok(pendingOrders);
    }
}
