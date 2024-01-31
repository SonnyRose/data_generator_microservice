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

public class DataMapperTest {
    @InjectMocks
    private DataMapperImpl dataMapper;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMappingToDtoAndToEntity() {
        Data dataEntity = new Data();
        dataEntity.setSensorId(1L);
        dataEntity.setTimeStamp(LocalDateTime.parse("2022-01-01T12:34:56"));
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

        Data mappedDataEntity = dataMapper.toEntity(dataDTO);

        assertThat(mappedDataEntity.getSensorId())
                .isEqualTo(1L);
        assertThat(mappedDataEntity.getTimeStamp())
                .isEqualTo(LocalDateTime.parse("2022-01-01T12:34:56"));
        assertThat(mappedDataEntity.getMeasurement())
                .isEqualTo(42.0);
        assertThat(mappedDataEntity.getMeasurementType())
                .isEqualTo(MeasurementType.TEMPERATURE);
    }
}
