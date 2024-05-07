package com.example.SpringMetheoAPI.service;

import com.example.SpringMetheoAPI.api.model.MeteoData;
import com.example.SpringMetheoAPI.api.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MeteoService {
    private final MeteoRepository meteoRepository;

    @Autowired
    public MeteoService(MeteoRepository meteoRepository) {
        this.meteoRepository = meteoRepository;
    }

    public MeteoData saveMeteoData(MeteoData meteoData) {
        return meteoRepository.save(meteoData);
    }

    public List<MeteoData> getAllMeteoData() {
        return meteoRepository.findAll();
    }

    public MeteoData getMeteoDataById(String id) {
        return meteoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MeteoData not found with id: " + id));
    }
}
