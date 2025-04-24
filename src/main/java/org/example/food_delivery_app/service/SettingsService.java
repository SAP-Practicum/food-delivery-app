package org.example.food_delivery_app.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.food_delivery_app.model.Settings;
import org.example.food_delivery_app.repository.SettingsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private final SettingsRepository settingsRepository;


    @PostConstruct
    public void initializeSettings(){
        if(settingsRepository.findFirstByOrderByIdAsc() == null){
            Settings defaultSettings = new Settings();
            defaultSettings.setBonusPercentage(10.0);
            defaultSettings.setBonusThreshold(1500.0);
            settingsRepository.save(defaultSettings);
            System.out.println("Settings saved");
        }
    }

    public Settings getSettings(){
        return settingsRepository.findFirstByOrderByIdAsc();
    }

    public Settings updateSettings(double bonusPercentage, double bonusThreshold){
        Settings settings = getSettings();
        if(settings == null){
            settings = new Settings();
        }

        if(bonusPercentage >=10.0){
            settings.setBonusPercentage(bonusPercentage);
        }else {
            throw new IllegalArgumentException("bonusPercentage cannot be less than 10.0");
        }

        settings.setBonusThreshold(bonusThreshold);
        return settingsRepository.save(settings);
    }
}
