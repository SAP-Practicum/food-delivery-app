package org.example.food_delivery_app.Tasks;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.service.DeliveryService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final DeliveryService deliveryService;

    @Scheduled(cron = "0 * * * * ?")
    public void checkMonthlyEarningsAndApplyBonus(){

        System.out.println("Applying monthly bonuses to eligible deliveries...");
        LocalDate now = LocalDate.now();
        LocalDateTime startDate = now.minusMonths(1).withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = now.withDayOfMonth(1).atStartOfDay();

        deliveryService.checkAndApplyMonthlyBonuses(startDate, endDate);

    }
}
