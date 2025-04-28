package org.example.food_delivery_app.controllers;

import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.service.SettingsService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SettingsControllerTest {

    @Mock
    private SettingsService settingsService;

    @InjectMocks
    private SettingsController settingsController;

    public SettingsControllerTest() {
        MockitoAnnotations.openMocks(this); // Инициализира Mock обектите
    }

    @Test
    void getSettings_shouldReturnSettingsSuccessfully() {
        Settings settings = new Settings();
        settings.setBonusPercentage(15.0);
        settings.setBonusThreshold(2000.0);

        when(settingsService.getSettings()).thenReturn(settings);

        ResponseEntity<Settings> response = settingsController.getSettings();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(15.0, response.getBody().getBonusPercentage());
        assertEquals(2000.0, response.getBody().getBonusThreshold());
    }

    @Test
    void updateSettings_shouldReturnUpdatedSettings() {
        Settings updatedSettings = new Settings();
        updatedSettings.setBonusPercentage(20.0);
        updatedSettings.setBonusThreshold(2500.0);

        when(settingsService.updateSettings(20.0, 2500.0)).thenReturn(updatedSettings);

        ResponseEntity<Settings> response = settingsController.updateSettings(20.0, 2500.0);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(20.0, response.getBody().getBonusPercentage());
        assertEquals(2500.0, response.getBody().getBonusThreshold());
    }

    @Test
    void updateSettings_shouldReturnBadRequestWhenInvalidBonus() {
        when(settingsService.updateSettings(5.0, 1500.0)).thenThrow(new IllegalArgumentException("bonusPercentage cannot be less than 10.0"));

        ResponseEntity<Settings> response = settingsController.updateSettings(5.0, 1500.0);

        assertEquals(400, response.getStatusCodeValue());
        assertNull(response.getBody());
    }
}
