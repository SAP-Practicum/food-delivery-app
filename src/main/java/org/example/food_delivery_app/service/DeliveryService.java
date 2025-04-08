package org.example.food_delivery_app.service;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Delivery;
import org.example.food_delivery_app.model.Order;
import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.repository.DeliveryRepository;
import org.example.food_delivery_app.repository.OrderRepository;
import org.example.food_delivery_app.repository.SettingsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final OrderRepository orderRepository;
    private final SettingsRepository settingsRepository;
    private final DeliveryRepository deliveryRepository;

    public double calculateEarningsWithBonus(Long deliveryId, LocalDateTime startDate, LocalDateTime endDate) {

        List<Order> orders = orderRepository.findAllByDeliveryIdAndCreatedDateBetween(deliveryId, startDate, endDate);

        double totalEarnings = orders.stream()
                .mapToDouble(Order::getTotalPrice)
                .sum();

        Settings settings = settingsRepository.findFirstByOrderByIdAsc();

        if(settings != null && totalEarnings > settings.getBonusThreshold()) {
            double bonus = totalEarnings *(settings.getBonusPercentage()/100);
            totalEarnings+=bonus;
        }

        return totalEarnings;
    }

    public void checkAndApplyMonthlyBonuses(LocalDateTime startDate, LocalDateTime endDate) {
        Settings settings = settingsRepository.findFirstByOrderByIdAsc();


        if(settings == null){
            settings = new Settings();
            settingsRepository.save(settings);
        }

        List<Delivery> deliveries = deliveryRepository.findAll();

        for(Delivery delivery : deliveries){
            List<Order> orders = orderRepository.findAllByDeliveryIdAndCreatedDateBetween(
                    delivery.getId(), startDate, endDate);
            double totalEarnings = orders.stream().mapToDouble(Order::getTotalPrice).sum();

            if (totalEarnings >= settings.getBonusThreshold()){
                double bonus = totalEarnings *(settings.getBonusPercentage()/100);
                System.out.println("Delivery Id "+ delivery.getId() + " earned bonus" + bonus);
            }
        }
    }


}
