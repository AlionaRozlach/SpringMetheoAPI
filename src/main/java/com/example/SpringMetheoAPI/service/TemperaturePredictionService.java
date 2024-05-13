package com.example.SpringMetheoAPI.service;

import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemperaturePredictionService {
    private final MeteoRepository meteoRepository;

    @Autowired
    public TemperaturePredictionService(MeteoRepository meteoRepository) {
        this.meteoRepository = meteoRepository;
    }

    public List<Double> getTemperatureList() {
        List<MeteoData> weatherList = meteoRepository.findAll();
        List<Double> temperatures = new ArrayList<>();

        for (MeteoData data : weatherList) {
            temperatures.add(data.getTemperature().getValue());
        }

        return temperatures;
    }

    public double averageTemperatureDifference() {
        List<Double> temperatures  = getTemperatureList();
        double sumDifference = 0;

        for (int i = 1; i < temperatures.size(); i++) {
            double difference = temperatures.get(i) - temperatures.get(i - 1);
            sumDifference += difference;
        }

        return sumDifference / (temperatures.size() - 1);
    }

    public double predictNextTemperature() {
        List<Double> temperatures  = getTemperatureList();
        double lastTemperature = temperatures.get(temperatures.size() - 1);
        double averageDifference = averageTemperatureDifference();
        return lastTemperature + averageDifference;
    }
}
