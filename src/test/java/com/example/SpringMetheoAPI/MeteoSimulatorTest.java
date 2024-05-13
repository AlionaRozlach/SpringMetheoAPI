package com.example.SpringMetheoAPI;

import com.example.SpringMetheoAPI.model.Humidity;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.model.WindSpeed;
import com.example.SpringMetheoAPI.repository.MeteoRepository;
import com.example.SpringMetheoAPI.simulator.MeteoSimulator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MeteoSimulatorTest {

    @Mock
    private MeteoRepository meteoRepository;

    @InjectMocks
    private MeteoSimulator meteoSimulator;

    @Test
    void testGenerateWeatherDataAndSave() {
        // Given
        MeteoData mockMeteoData = new MeteoData("1", LocalDateTime.now(), new Temperature(25.0), new Humidity(50), new WindSpeed(10));
        when(meteoRepository.save(any(MeteoData.class))).thenReturn(mockMeteoData);

        // When
        meteoSimulator.processData();

        // Then
        verify(meteoRepository, times(1)).save(any(MeteoData.class));
    }

    @Test
    void testGenerateWeatherDataWithinRange() {
        // Given
        MeteoSimulator meteoSimulator = new MeteoSimulator();

        // When
        MeteoData meteoData = meteoSimulator.generateWeatherData();

        // Then
        assertTrue(meteoData.getTemperature().getValue() >= 10.0 && meteoData.getTemperature().getValue() <= 30.0);
        assertTrue(meteoData.getHumidity().getValue() >= 0 && meteoData.getHumidity().getValue() <= 100);
        assertTrue(meteoData.getWindSpeed().getValue() >= 0 && meteoData.getWindSpeed().getValue() <= 50);
    }
}
