package org.example.food_delivery_app.Controller;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.service.DeliveryService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/earnings")
    public ResponseEntity<Double> getEarningsWithBonus(
            @RequestParam Long deliveryId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ){
        double earnings = deliveryService.calculateEarningsWithBonus(deliveryId, startDate, endDate);
        return ResponseEntity.ok(earnings);
    }

}
