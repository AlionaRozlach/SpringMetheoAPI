package com.example.SpringMetheoAPI.simulator;

import com.example.SpringMetheoAPI.model.Humidity;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.model.WindSpeed;
import com.example.SpringMetheoAPI.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;
import java.time.LocalDateTime;

@Component
public class MeteoSimulator implements CommandLineRunner {
    private final List<Thread> simulators = new ArrayList<>();
    private final Random random = new Random();

    @Autowired
    private MeteoRepository repository;

    @Autowired
    public MeteoSimulator() {
    }

    public MeteoData generateWeatherData() {
        Temperature temperature = new Temperature(generateRandomDouble(10, 30));
        Humidity humidity = new Humidity(generateRandomDouble(0, 100));
        WindSpeed windSpeed = new WindSpeed(generateRandomDouble(0, 50));
        LocalDateTime timestamp = LocalDateTime.now();
        String id = UUID.randomUUID().toString();
        return new MeteoData(id, timestamp, temperature, humidity, windSpeed);
    }

    private double generateRandomDouble(double min, double max) {
        return min + random.nextDouble() * (max - min);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of weather stations to simulate:");
        int numberOfStations = scanner.nextInt();

        for (int i = 0; i < numberOfStations; i++) {
            Thread simulatorThread = new Thread(() -> {
                while (true) {
                    processData();
                    try {
                        Thread.sleep(3600000); //sleep 1 hour
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            simulators.add(simulatorThread);
            simulatorThread.start();
        }

        scanner.close();
    }

    public void processData() {
        MeteoData meteoData = generateWeatherData();
        repository.save(meteoData);
        System.out.println("Generated weather data: " + meteoData);
    }
}
