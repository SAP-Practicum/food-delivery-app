package org.example.food_delivery_app.Controller;


import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.service.SettingsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingsController {

    private final SettingsService settingsService;

    @GetMapping
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Settings> getSettings() {
        return ResponseEntity.ok(settingsService.getSettings());
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<Settings> updateSettings(@RequestParam double bonusPercentage,
                                                   @RequestParam double bonusThreshold) {
        try {
            Settings updateSettings = settingsService.updateSettings(bonusPercentage, bonusThreshold);
            return ResponseEntity.ok(updateSettings);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
