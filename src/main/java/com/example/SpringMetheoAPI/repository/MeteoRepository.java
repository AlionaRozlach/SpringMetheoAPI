package com.example.SpringMetheoAPI.repository;
import com.example.SpringMetheoAPI.model.MeteoData;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface MeteoRepository extends MongoRepository<MeteoData, String> {
    List<MeteoData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
