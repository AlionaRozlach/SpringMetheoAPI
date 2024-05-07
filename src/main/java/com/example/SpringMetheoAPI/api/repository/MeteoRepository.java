package com.example.SpringMetheoAPI.api.repository;

import com.example.SpringMetheoAPI.api.model.MeteoData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MeteoRepository extends MongoRepository<MeteoData, String> {

    List<MeteoData> findAll();

    MeteoData findMeteoDataById(String id);

    <S extends MeteoData> S save(S entity);

    void delete(MeteoData entity);

    void deleteById(String id);

    boolean existsById(String id);

    List<MeteoData> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
}
