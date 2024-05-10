package com.example.SpringMetheoAPI.utils;

import com.example.SpringMetheoAPI.model.MeteoData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.xssf.usermodel.*;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class ExcelUtil {
    public static byte[] generateExcelFromMeteoData(List<MeteoData> meteoDataList) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Meteo Data");

        XSSFRow headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Timestamp");
        headerRow.createCell(1).setCellValue("Temperature");
        headerRow.createCell(2).setCellValue("Humidity");
        headerRow.createCell(3).setCellValue("Wind Speed");

        int rowNum = 1;
        for (MeteoData meteoData : meteoDataList) {
            XSSFRow  row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(meteoData.getTimestamp().toString());
            row.createCell(1).setCellValue(meteoData.getTemperature().getValue());
            row.createCell(2).setCellValue(meteoData.getHumidity().getValue());
            row.createCell(3).setCellValue(meteoData.getWindSpeed().getValue());
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    public static HttpHeaders createExcelHeaders(String filename) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(filename).build());
        return headers;
    }
}
