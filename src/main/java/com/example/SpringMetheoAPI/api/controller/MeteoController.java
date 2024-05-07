package com.example.SpringMetheoAPI.api.controller;

import com.example.SpringMetheoAPI.api.model.MeteoData;
import com.example.SpringMetheoAPI.api.repository.MeteoRepository;
import com.example.SpringMetheoAPI.service.MeteoService;
import com.example.SpringMetheoAPI.utils.ExcelUtil;
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
    private MeteoService meteoService;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/data")
    public ResponseEntity<byte[]> getMeteoDataByTimeInterval(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<MeteoData> meteoDataList = meteoService.getMeteoDataByTimeInterval(start, end);
            byte[] excelBytes = ExcelUtil.generateExcelFromMeteoData(meteoDataList);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("meteo_data3.xlsx").build());

            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
