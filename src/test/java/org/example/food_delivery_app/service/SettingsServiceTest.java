package org.example.food_delivery_app.service;

import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.repository.SettingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SettingsServiceTest {

    @Mock
    private SettingsRepository settingsRepository;

    @InjectMocks
    private SettingsService settingsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testInitializeSettings_NotExisting() {
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(null);

        settingsService.initializeSettings();

        verify(settingsRepository,times(1)).save(any(Settings.class));
    }

    @Test
    public void testInitializeSettings_AlreadyExisting() {
        Settings settings = new Settings();
        settings.setId(1L);
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        settingsService.initializeSettings();

        verify(settingsRepository,never()).save(settings);
    }

    @Test
    public void testGetSettings(){
        Settings settings = new Settings();
        settings.setId(1L);
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        Settings actualSettings = settingsService.getSettings();

        assertEquals(settings,actualSettings);
    }

    @Test
    public void testUpdateSettings_ValidSettings() {
        Settings settings = new Settings();
        settings.setId(1L);
        settings.setBonusPercentage(10.0);
        settings.setBonusThreshold(1500.0);
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        double bonusPercentage = 15;
        double bonusThreshold = 2000;

        when(settingsRepository.save(any(Settings.class)))
                .thenAnswer(invocation -> {
                    Settings savedSettings = invocation.getArgument(0);
                    savedSettings.setId(1L);
                    return savedSettings;
                });

        Settings updatedSettings = settingsService.updateSettings(bonusPercentage, bonusThreshold);

        assertNotNull(updatedSettings);
        assertEquals(bonusPercentage,updatedSettings.getBonusPercentage());
        assertEquals(bonusThreshold,updatedSettings.getBonusThreshold());
        verify(settingsRepository,times(1)).save(settings);
    }

    @Test
    public void testUpdateSettings_InvalidSettings() {
        Settings settings = new Settings();
        settings.setId(1L);
        settings.setBonusPercentage(10.0);
        settings.setBonusThreshold(1500.0);
        when(settingsRepository.findFirstByOrderByIdAsc()).thenReturn(settings);

        double bonusPercentage = 5.0;
        double bonusThreshold = 2000.0;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> settingsService.updateSettings(bonusPercentage, bonusThreshold));

        assertEquals("bonusPercentage cannot be less than 10.0", exception.getMessage());

        verify(settingsRepository,never()).save(settings);
    }
}