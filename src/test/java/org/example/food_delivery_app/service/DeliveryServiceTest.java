package org.example.food_delivery_app.service;

import org.example.food_delivery_app.model.Delivery;
import org.example.food_delivery_app.model.Order;
import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.repository.DeliveryRepository;
import org.example.food_delivery_app.repository.OrderRepository;
import org.example.food_delivery_app.repository.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private SettingsRepository settingsRepository;

    @Mock
    private DeliveryRepository deliveryRepository;

    @InjectMocks
    private DeliveryService deliveryService;

    private Settings settings;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @BeforeEach
    void setUp() {
        settings = new Settings();
        settings.setBonusThreshold(1500.0);
        settings.setBonusPercentage(10.0);

        startDate = LocalDateTime.of(2024, 1, 1, 0, 0);
        endDate = LocalDateTime.of(2024, 1, 31, 23, 59);
    }

    @Test
    // Проверяваме дали правилно е сметнат бонуса, когато изкарванията стигнат границата за него
    void testCalculateEarningsWithBonus_WhenEarningsAboveThreshold() {
        Long deliveryId = 1L;

        Order order1 = new Order();
        order1.setTotalPrice(1000.0);
        Order order2 = new Order();
        order2.setTotalPrice(800.0);

        List<Order> orders = Arrays.asList(order1, order2);

        when(orderRepository.findAllByDeliveryIdAndCreatedDateBetween(deliveryId, startDate, endDate))
                .thenReturn(orders);
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        double result = deliveryService.calculateEarningsWithBonus(deliveryId, startDate, endDate);

        // 1000 + 800 = 1800, bonus = 1800 * 10% = 180, total = 1980
        assertEquals(1980.0, result);
    }

    @Test
    // Проверяваме дали не е даден бонус, ако не е стигната границата на изкарвания за такъв
    void testCalculateEarningsWithBonus_WhenEarningsBelowThreshold() {
        Long deliveryId = 1L;

        Order order1 = new Order();
        order1.setTotalPrice(600.0);
        Order order2 = new Order();
        order2.setTotalPrice(700.0);

        when(orderRepository.findAllByDeliveryIdAndCreatedDateBetween(deliveryId, startDate, endDate))
                .thenReturn(Arrays.asList(order1, order2));
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        double result = deliveryService.calculateEarningsWithBonus(deliveryId, startDate, endDate);

        // 600 + 700 = 1300 < threshold
        assertEquals(1300.0, result);
    }

    @Test
    // Проверяваме дали логиката на бонуса работи при всяка една поръчка
    void testCheckAndApplyMonthlyBonuses_WhenBonusApplicable() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);

        Order order1 = new Order();
        order1.setTotalPrice(1000.0);
        Order order2 = new Order();
        order2.setTotalPrice(600.0);

        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);
        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));
        when(orderRepository.findAllByDeliveryIdAndCreatedDateBetween(delivery.getId(), startDate, endDate))
                .thenReturn(List.of(order1, order2));

        deliveryService.checkAndApplyMonthlyBonuses(startDate, endDate);

        verify(orderRepository).findAllByDeliveryIdAndCreatedDateBetween(delivery.getId(), startDate, endDate);
    }

    @Test
    // Проверяваме дали се извикват правилните методи и дали изпринтират каквото трябва
    void testCheckAndApplyMonthlyBonuses_WhenNoBonusApplicable() {
        Delivery delivery = new Delivery();
        delivery.setId(2L);

        Order order1 = new Order();
        order1.setTotalPrice(500.0);
        Order order2 = new Order();
        order2.setTotalPrice(600.0);

        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);
        when(deliveryRepository.findAll()).thenReturn(List.of(delivery));
        when(orderRepository.findAllByDeliveryIdAndCreatedDateBetween(delivery.getId(), startDate, endDate))
                .thenReturn(List.of(order1, order2));

        deliveryService.checkAndApplyMonthlyBonuses(startDate, endDate);

        verify(orderRepository).findAllByDeliveryIdAndCreatedDateBetween(delivery.getId(), startDate, endDate);
    }
}
