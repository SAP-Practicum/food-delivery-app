package org.example.food_delivery_app.service;

import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.*;
import org.example.food_delivery_app.repository.CustomerRepository;
import org.example.food_delivery_app.repository.DeliveryRepository;
import org.example.food_delivery_app.repository.OrderRepository;
import org.example.food_delivery_app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final DeliveryRepository deliveryRepository;

    public Double getRestaurantRevenue(Long restaurantId, LocalDateTime startDate, LocalDateTime endDate) {
        Double revenue = orderRepository.calculateRevenue(restaurantId, startDate, endDate);
        return revenue != null ? revenue :0.0;
    }

    public Order createOrder(Long customerId, List<Long> productIds){
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        List<Product> products = productRepository.findAllById(productIds);

        double totalPrice = products.stream().mapToDouble(Product::getPrice).sum();

        Order order = new Order();
        order.setCustomer(customer);
        order.setProducts(products);
        order.setTotalPrice(totalPrice);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersByCustomerId(Long customerId){
        return orderRepository.findByCustomerId(customerId);
    }

    public List<Order>getAllPendingOrders(){
        return orderRepository.findByStatus(OrderStatus.PENDING);
    }

    public void acceptOrder(Long orderId, Long deliveryId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if(order.getStatus() == OrderStatus.ACCEPTED||order.getStatus() == OrderStatus.DELIVERED){
            throw new RuntimeException("Order is already accepted");
        }

        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(()-> new RuntimeException("Delivery not found"));

        order.setDelivery(delivery);
        order.setStatus(OrderStatus.ACCEPTED);

        orderRepository.save(order);
    }

    public void deliveredOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PENDING){
            throw new RuntimeException("Order is not accepted to be delivered");
        }else if (order.getStatus() == OrderStatus.DELIVERED){
            throw new RuntimeException("Order is already delivered");
        }

        order.setStatus(OrderStatus.DELIVERED);

        orderRepository.save(order);
    }

    public OrderStatus getOrderStatus(Long orderId, Long customerId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if(!order.getCustomer().getId().equals(customerId)){
            throw new RuntimeException("this customer does not own the owner");
        }
        return order.getStatus();
    }

    public double getEarningsForDelivery(Long deliveryId, LocalDateTime startDate,LocalDateTime endDate){

        List<Order> orders = orderRepository.findAllByDeliveryIdAndCreatedDateBetween(deliveryId, startDate, endDate);

        return orders.stream()
                 .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                 .mapToDouble(Order::getTotalPrice)
                 .sum();
    }

    public void preparingOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if(order.getStatus() != OrderStatus.PENDING){
            throw new RuntimeException("Order is not accepted to be preparing");
        }
        order.setStatus(OrderStatus.PREPARING);
        orderRepository.save(order);
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order not found"));

        if(order.getStatus() != OrderStatus.PENDING){
            throw new IllegalStateException("Order is already accepted or cancelled");
        }

        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    public List<Order> getPendingOrdersByRestaurantId(Long restaurantId){
        return orderRepository.findPendingOrdersByRestaurantId(restaurantId);
    }
}
