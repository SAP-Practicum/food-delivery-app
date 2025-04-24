package org.example.food_delivery_app.service;

import org.example.food_delivery_app.model.*;
import org.example.food_delivery_app.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    // Проверяваме дали метода createOrder правилно създава и запазва поръчка
    void testCreateOrder_Success() {
    Long customerId = 1L;
    List<Long> productIds = Arrays.asList(1L, 2L);

    Customer customer = new Customer();
    customer.setId(customerId);

    Product product1 = new Product();
    product1.setId(1L);
    product1.setPrice(10.0);

    Product product2 = new Product();
    product2.setId(2L);
    product2.setPrice(15.0);

    List<Product> products = Arrays.asList(product1, product2);

    when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
    when(productRepository.findAllById(productIds)).thenReturn(products);

    Order savedOrder = new Order();
    savedOrder.setId(1L);
    savedOrder.setCustomer(customer);
    savedOrder.setProducts(products);
    savedOrder.setTotalPrice(25.0);
    savedOrder.setStatus(OrderStatus.PENDING);
    savedOrder.setCreatedDate(LocalDateTime.now());

    when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

    Order result = orderService.createOrder(customerId, productIds);

    assertNotNull(result);
    assertEquals(25.0, result.getTotalPrice());
    assertEquals(OrderStatus.PENDING, result.getStatus());
    assertEquals(customer, result.getCustomer());
    assertEquals(products, result.getProducts());
    }

    @Test
    // Проверяваме calculateRevenue метода, който връща общия оборот на ресторант м/у две дати
    void testGetRestaurantRevenue_WithRevenue() {
    Long restaurantId = 1L;
    LocalDateTime startDate = LocalDateTime.now().minusDays(7);
    LocalDateTime endDate = LocalDateTime.now();
    Double expectedRevenue = 1000.0;

    when(orderRepository.calculateRevenue(restaurantId, startDate, endDate)).thenReturn(expectedRevenue);

    Double result = orderService.getRestaurantRevenue(restaurantId, startDate, endDate);

    assertEquals(expectedRevenue, result);
    }

    @Test
    // същото като горния тест за ресторант без оборот за период
    void testGetRestaurantRevenue_NoRevenue() {
    Long restaurantId = 1L;
    LocalDateTime startDate = LocalDateTime.now().minusDays(7);
    LocalDateTime endDate = LocalDateTime.now();

    when(orderRepository.calculateRevenue(restaurantId, startDate, endDate)).thenReturn(null);

    Double result = orderService.getRestaurantRevenue(restaurantId, startDate, endDate);

    assertEquals(0.0, result);
    }

    @Test
    // Проверяваме acceptOrder метода, който назначава доставчик към поръчка и променя статуса й
    void testAcceptOrder_Success() {
    Long orderId = 1L;
    Long deliveryId = 2L;

    Order order = new Order();
    order.setId(orderId);
    order.setStatus(OrderStatus.PENDING);

    Delivery delivery = new Delivery();
    delivery.setId(deliveryId);

    when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
    when(deliveryRepository.findById(deliveryId)).thenReturn(Optional.of(delivery));
    when(orderRepository.save(any(Order.class))).thenReturn(order);

    orderService.acceptOrder(orderId, deliveryId);

    assertEquals(OrderStatus.ACCEPTED, order.getStatus());
    assertEquals(delivery, order.getDelivery());
    }

    @Test
    // Проверяваме търсенето на поръчки по ID на клиент
    void testGetOrdersByCustomerId() {
    Long customerId = 1L;
    List<Order> orders = Arrays.asList(new Order(), new Order());

    when(orderRepository.findByCustomerId(customerId)).thenReturn(orders);

    List<Order> result = orderService.getOrdersByCustomerId(customerId);

    assertEquals(2, result.size());
    }




}
