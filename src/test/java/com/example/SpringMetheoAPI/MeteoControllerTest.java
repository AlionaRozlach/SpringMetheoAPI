package com.example.SpringMetheoAPI;

import com.example.SpringMetheoAPI.controller.MeteoController;
import com.example.SpringMetheoAPI.model.Humidity;
import com.example.SpringMetheoAPI.model.MeteoData;
import com.example.SpringMetheoAPI.model.Temperature;
import com.example.SpringMetheoAPI.model.WindSpeed;
import com.example.SpringMetheoAPI.service.MeteoService;
import com.example.SpringMetheoAPI.service.TemperaturePredictionService;
import com.example.SpringMetheoAPI.simulator.MeteoSimulator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@AutoConfigureMockMvc
public class MeteoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeteoService meteoService;

    @MockBean
    private TemperaturePredictionService temperaturePredictionService;

    @MockBean
    private MeteoSimulator simulator;

    @Autowired
    private MeteoController meteoController;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");

    @Test
    public void testGetMeteoDataByTimeInterval() throws Exception {
        // Mock MeteoData list
        List<MeteoData> mockMeteoDataList = Arrays.asList(
                new MeteoData("1", LocalDateTime.of(2024, 5, 13, 12, 0), new Temperature(20.0), new Humidity(10), new WindSpeed(25)),
                new MeteoData("2", LocalDateTime.of(2024, 5, 13, 13, 0), new Temperature(22.0), new Humidity(15), new WindSpeed(30))
        );

        // Mock MeteoService to return the mock MeteoData list
        when(meteoService.getMeteoDataByTimeInterval(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockMeteoDataList);

        // Perform GET request
        MvcResult result = mockMvc.perform(get("/api/meteo/data")
                .param("start", "2024-05-13T00:00:00")
                .param("end", "2024-05-13T23:59:59"))
                .andExpect(status().isOk())
                .andReturn();

        // Verify response content
        byte[] excelBytes = result.getResponse().getContentAsByteArray();
        String contentType = result.getResponse().getContentType();

        // Assert response contains Excel file with correct content type
        assertThat(contentType).isEqualTo("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        // Assert response contains header with filename "meteo_data.xlsx"
        String contentDisposition = result.getResponse().getHeader("Content-Disposition");
        assertThat(contentDisposition).contains("meteo_data.xlsx");
    }

    @Test
    public void testPredictNextTemperature() {
        // Mock prediction value
        double predictedTemperature = 22.0;
        when(temperaturePredictionService.predictNextTemperature()).thenReturn(predictedTemperature);

        // Call the method
        double actualTemperature = meteoController.predictNextTemperature();

        // Verify the result
        assertEquals(predictedTemperature, actualTemperature, 0.01); // Use delta for double comparison
    }
}
