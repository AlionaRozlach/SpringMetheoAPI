package com.example.SpringMetheoAPI.controller;

import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.service.MeteoService;
import com.example.SpringMetheoAPI.simulator.MeteoSimulator;
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
    private MeteoSimulator weatherSimulator;

    @PostMapping("/save")
    public MeteoData saveMeteoData(@RequestBody MeteoData meteoData) {
        return meteoService.saveMeteoData(meteoData);
    }

    @GetMapping("/all")
    public List<MeteoData> getAllMeteoData() {
        return meteoService.getAllMeteoData();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MeteoData> getMeteoDataById(@PathVariable(value = "id") String id) {
            MeteoData meteoData = meteoService.getMeteoDataById(id);
            return ResponseEntity.ok().body(meteoData);
    }

    @GetMapping("/data")
    public ResponseEntity<byte[]> getMeteoDataByTimeInterval(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        try {
            List<MeteoData> meteoDataList = meteoService.getMeteoDataByTimeInterval(start, end);
            byte[] excelBytes = ExcelUtil.generateExcelFromMeteoData(meteoDataList);
            String fileName = "meteo_data.xlsx";
            HttpHeaders headers = ExcelUtil.createExcelHeaders(fileName);
            return ResponseEntity.ok().headers(headers).body(excelBytes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
