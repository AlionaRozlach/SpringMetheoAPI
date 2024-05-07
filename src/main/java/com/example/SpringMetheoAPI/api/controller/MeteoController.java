package com.example.SpringMetheoAPI.api.controller;

import com.example.SpringMetheoAPI.api.model.MeteoData;
import com.example.SpringMetheoAPI.api.repository.MeteoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/meteo")
public class MeteoController {
    @Autowired
    private MeteoRepository meteoDataRepository;

    @PostMapping("/save")
    public MeteoData saveMeteoData(@RequestBody MeteoData meteoData) {
        return meteoDataRepository.save(meteoData);
    }

    @GetMapping("/all")
    public List<MeteoData> getAllMeteoData() {
        return meteoDataRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeteoData> getMeteoDataById(@PathVariable(value = "id") String id) {
        try {
            MeteoData meteoData = meteoDataRepository.findMeteoDataById(id);
            if (meteoData != null) {
                return ResponseEntity.ok().body(meteoData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Обработка исключения, если необходимо
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
