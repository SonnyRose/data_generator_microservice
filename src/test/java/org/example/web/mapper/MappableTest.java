package org.example.web.mapper;

import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.web.DTO.DataDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MappableTest {
    @InjectMocks
    private DataMapperImpl dataMapper;
    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDto() {
        Data dataEntity = new Data();
        dataEntity.setSensorId(1L);
        dataEntity
                .setTimeStamp(LocalDateTime.parse("2022-01-01T12:34:56"));
        dataEntity.setMeasurement(42.0);
        dataEntity.setMeasurementType(MeasurementType.TEMPERATURE);

        DataDTO dataDTO = dataMapper.toDTO(dataEntity);

        assertThat(dataDTO.getSensorId())
                .isEqualTo(1L);
        assertThat(dataDTO.getTimeStamp())
                .isEqualTo(LocalDateTime.parse("2022-01-01T12:34:56"));
        assertThat(dataDTO.getMeasurement())
                .isEqualTo(42.0);
        assertThat(dataDTO.getMeasurementType())
                .isEqualTo(MeasurementType.TEMPERATURE);
    }
    @Test
    public void testToEntity() {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setSensorId(1L);
        dataDTO.setTimeStamp(LocalDateTime.parse("2022-01-01T12:34:56"));
        dataDTO.setMeasurement(42.0);
        dataDTO.setMeasurementType(MeasurementType.TEMPERATURE);

        Data dataEntity = dataMapper.toEntity(dataDTO);

        assertThat(dataEntity.getSensorId())
                .isEqualTo(1L);
        assertThat(dataEntity.getTimeStamp())
                .isEqualTo(LocalDateTime.parse("2022-01-01T12:34:56"));
        assertThat(dataEntity.getMeasurement())
                .isEqualTo(42.0);
        assertThat(dataEntity.getMeasurementType())
                .isEqualTo(MeasurementType.TEMPERATURE);
    }
}
