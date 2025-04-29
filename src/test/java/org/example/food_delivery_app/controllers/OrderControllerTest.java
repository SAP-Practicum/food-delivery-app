package org.example.food_delivery_app.controllers;

import org.example.food_delivery_app.model.Order;
import org.example.food_delivery_app.model.OrderStatus;
import org.example.food_delivery_app.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getRestaurantRevenue_shouldReturnRevenue() {
        when(orderService.getRestaurantRevenue(1L, LocalDateTime.MIN, LocalDateTime.MAX))
                .thenReturn(500.0);

        ResponseEntity<Double> response = orderController.getRestaurantRevenue(1L, LocalDateTime.MIN, LocalDateTime.MAX);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(500.0, response.getBody());
    }

    @Test
    void createOrder_shouldReturnCreatedOrder() {
        Order order = new Order();
        order.setId(1L);

        when(orderService.createOrder(eq(1L), anyList())).thenReturn(order);

        ResponseEntity<Order> response = orderController.createOrder(1L, Arrays.asList(1L, 2L));

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getOrdersByCustomerId_shouldReturnOrders() {
        Order order1 = new Order();
        order1.setId(1L);

        when(orderService.getOrdersByCustomerId(1L)).thenReturn(Arrays.asList(order1));

        ResponseEntity<List<Order>> response = orderController.getOrdersByCustomerId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void getAllPendingOrders_shouldReturnPendingOrders() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);

        when(orderService.getAllPendingOrders()).thenReturn(Arrays.asList(order));

        ResponseEntity<List<Order>> response = orderController.getAllPendingOrders();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(OrderStatus.PENDING, response.getBody().get(0).getStatus());
    }

    @Test
    void acceptOrder_shouldReturnSuccessMessage() {
        doNothing().when(orderService).acceptOrder(1L, 2L);

        ResponseEntity<String> response = orderController.acceptOrder(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Order accepted successfully"));
    }

    @Test
    void updateOrderStatus_shouldReturnDeliveredMessage() {
        doNothing().when(orderService).deliveredOrder(1L);

        ResponseEntity<String> response = orderController.updateOrderStatus(1L, 2L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Order is delivered"));
    }

    @Test
    void trackOrder_shouldReturnOrderStatus() {

    }

    @Test
    void getEarningsForDelivery_shouldReturnEarnings() {
        when(orderService.getEarningsForDelivery(1L, LocalDateTime.MIN, LocalDateTime.MAX)).thenReturn(300.0);

        ResponseEntity<Double> response = orderController.getEarningsForDelivery(1L, LocalDateTime.MIN, LocalDateTime.MAX);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(300.0, response.getBody());
    }

    @Test
    void prepareOrder_shouldReturnPreparedMessage() {
        doNothing().when(orderService).preparingOrder(1L);

        ResponseEntity<String> response = orderController.prepareOrder(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order prepared successfully", response.getBody());
    }

    @Test
    void cancelOrder_shouldReturnCancelledMessage() {
        doNothing().when(orderService).cancelOrder(1L);

        ResponseEntity<String> response = orderController.cancelOrder(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Order cancelled successfully", response.getBody());
    }

    @Test
    void getPendingOrdersByRestaurantId_shouldReturnPendingOrders() {
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);

        when(orderService.getPendingOrdersByRestaurantId(1L)).thenReturn(Arrays.asList(order));

        ResponseEntity<List<Order>> response = orderController.getPendingOrdersByRestaurantId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(OrderStatus.PENDING, response.getBody().get(0).getStatus());
    }
}
