package com.example.SpringMetheoAPI;

import com.example.SpringMetheoAPI.model.Humidity;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.model.WindSpeed;
import com.example.SpringMetheoAPI.repository.MeteoRepository;
import com.example.SpringMetheoAPI.service.TemperaturePredictionService;
import com.example.SpringMetheoAPI.simulator.MeteoSimulator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TemperaturePredictionServiceTest {

    @Mock
    private MeteoRepository meteoRepository;

    private TemperaturePredictionService temperaturePredictionService;

    @MockBean
    private MeteoSimulator simulator;

    private  List<MeteoData> mockWeatherList;
    @BeforeEach
    void setUp() {
        temperaturePredictionService = new TemperaturePredictionService(meteoRepository); // Initialize the service
        mockWeatherList = Arrays.asList(
                new MeteoData("1", null, new Temperature(20.0), null, null),
                new MeteoData("2", null, new Temperature(22.0), null, null),
                new MeteoData("3", null, new Temperature(24.0), null, null),
                new MeteoData("4", null, new Temperature(26.0), null, null)
        );

        // Mock the behavior of the MeteoRepository
        when(meteoRepository.findAll()).thenReturn(mockWeatherList);
    }

    @Test
    void getTemperatureList() {
        List<Double> temperatures = temperaturePredictionService.getTemperatureList();

        assertEquals(temperatures.get(0), mockWeatherList.get(0).getTemperature().getValue());
    }

    @Test
    void averageTemperatureDifference() {
        // When
        double actualAverageDifference = temperaturePredictionService.averageTemperatureDifference();

        // Then
        assertEquals(2.0, actualAverageDifference);
    }

    @Test
    void predictNextTemperature() {
        // When
        double nextTemperature = temperaturePredictionService.predictNextTemperature();

        // Then
        assertEquals(28.0, nextTemperature);
    }
}
