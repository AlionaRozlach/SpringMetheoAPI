package com.example.SpringMetheoAPI;

import com.example.SpringMetheoAPI.model.Humidity;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.model.WindSpeed;
import com.example.SpringMetheoAPI.utils.ExcelUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExcelUtilTest {
    @Test
    void generateExcelFromMeteoData_Success() throws IOException {
        // Given
        List<MeteoData> mockMeteoDataList = new ArrayList<>();
        mockMeteoDataList.add(new MeteoData("1", LocalDateTime.now(), new Temperature(20.0), new Humidity(50), new WindSpeed(10)));
        mockMeteoDataList.add(new MeteoData("2", LocalDateTime.now().plusHours(1), new Temperature(22.0), new Humidity(55), new WindSpeed(12)));

        // When
        byte[] excelBytes = ExcelUtil.generateExcelFromMeteoData(mockMeteoDataList);

        // Then
        assertNotNull(excelBytes);
        assertTrue(excelBytes.length > 0);
    }
}
