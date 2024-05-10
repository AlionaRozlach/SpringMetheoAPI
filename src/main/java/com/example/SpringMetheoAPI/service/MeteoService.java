package com.example.SpringMetheoAPI.service;

import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeteoService {
    private final MeteoRepository meteoRepository;

    @Autowired
    public MeteoService(MeteoRepository meteoRepository) {
        this.meteoRepository = meteoRepository;
    }

    public MeteoData saveMeteoData(MeteoData meteoData) {
        try {
            return meteoRepository.save(meteoData);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to save MeteoData", e);
        }
    }

    public List<MeteoData> getAllMeteoData() {
        List<MeteoData> allMeteoData = meteoRepository.findAll();
        if (allMeteoData.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No MeteoData found");
        }
        return allMeteoData;
    }

    public MeteoData getMeteoDataById(String id) {
        return meteoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "MeteoData not found with id: " + id));
    }

    public List<MeteoData> getMeteoDataByTimeInterval(LocalDateTime start, LocalDateTime end) {
        List<MeteoData> result = meteoRepository.findByTimestampBetween(start, end);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No MeteoData found in the specified time interval");
        }
        return result;
    }
}
