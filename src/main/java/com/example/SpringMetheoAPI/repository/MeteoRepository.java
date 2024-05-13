package com.example.SpringMetheoAPI.repository;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MeteoRepository extends MongoRepository<MeteoData, String> {
    List<MeteoData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
