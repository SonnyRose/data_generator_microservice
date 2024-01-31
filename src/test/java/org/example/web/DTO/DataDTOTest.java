package org.example.web.DTO;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.model.measurementType.MeasurementType;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataDTOTest {
    @Test
    public void gettersAndSettersTest() {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setSensorId(1L);
        dataDTO.setTimeStamp(LocalDateTime.of(2022,
                12,
                2,12,1,2));
        dataDTO.setMeasurement(42.0);
        dataDTO.setMeasurementType(MeasurementType.TEMPERATURE);

        assertEquals(1L, dataDTO.getSensorId());
        assertEquals(LocalDateTime.of(2022,12,
                2,12,1,2),
                dataDTO.getTimeStamp());
        assertEquals(42.0, dataDTO.getMeasurement());
        assertEquals(MeasurementType.TEMPERATURE, dataDTO.getMeasurementType());
    }
    @Test
    public void testSerialization() throws Exception {
        LocalDateTime timeStamp = LocalDateTime.parse("2022-01-01T12:34:56",
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        DataDTO dataDTO = new DataDTO();
        dataDTO.setSensorId(1L);
        dataDTO.setTimeStamp(timeStamp);
        dataDTO.setMeasurement(42.0);
        dataDTO.setMeasurementType(MeasurementType.TEMPERATURE);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String jsonString = objectMapper.writeValueAsString(dataDTO);

        String expectedJson = "{\"sensorId\":1,\"timeStamp\":\"2022-01-01T12:34:56\",\"measurement\":42.0,\"measurementType\":\"TEMPERATURE\"}";
        assertThat(jsonString).isEqualTo(expectedJson);
    }
    @Test
    public void testDeserialization() throws Exception {
        String jsonInput = "{\"sensorId\":1,\"timeStamp\":\"2022-01-01T12:34:56\",\"measurement\":42.0,\"measurementType\":\"TEMPERATURE\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        DataDTO deserializedDataDTO = objectMapper.readValue(jsonInput, DataDTO.class);

        assertThat(deserializedDataDTO.getSensorId())
                .isEqualTo(1L);
        assertThat(deserializedDataDTO.getTimeStamp())
                .isEqualTo(LocalDateTime.parse("2022-01-01T12:34:56",
                        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
        assertThat(deserializedDataDTO.getMeasurement())
                .isEqualTo(42.0);
        assertThat(deserializedDataDTO.getMeasurementType())
                .isEqualTo(MeasurementType.TEMPERATURE);
    }
}
