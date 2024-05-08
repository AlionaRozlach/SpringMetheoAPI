package com.example.SpringMetheoAPI.simulator;

import com.example.SpringMetheoAPI.api.model.Humidity;
import com.example.SpringMetheoAPI.api.model.MeteoData;
import com.example.SpringMetheoAPI.api.model.Temperature;
import com.example.SpringMetheoAPI.api.model.WindSpeed;
import java.util.UUID;

import java.time.LocalDateTime;
import java.util.Random;

public class MeteoSimulator {

    private Random random = new Random();

    public MeteoData generateWeatherData() {
        Temperature temperature = new Temperature(generateRandomDouble(10, 30));
        Humidity humidity = new Humidity(generateRandomDouble(0, 100));
        WindSpeed windSpeed = new WindSpeed(generateRandomDouble(0, 50));
        LocalDateTime timestamp = LocalDateTime.now();
        String id = UUID.randomUUID().toString();
        return new MeteoData(id,timestamp, temperature, humidity, windSpeed);
    }

    private double generateRandomDouble(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }
}
