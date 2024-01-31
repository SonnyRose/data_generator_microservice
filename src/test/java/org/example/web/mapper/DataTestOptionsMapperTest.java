package org.example.web.mapper;

import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.web.DTO.DataTestOptionsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;


public class DataTestOptionsMapperTest {
    @InjectMocks
    private DataTestOptionsMapperImpl dataTestOptionsMapperImpl;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testToDtoAndToEntity() {
        DataTestOptions dataTestOptions = new DataTestOptions();
        dataTestOptions.setDelayInSeconds(10);
        dataTestOptions
                .setMeasurementTypes(new MeasurementType[]{MeasurementType.TEMPERATURE,
                        MeasurementType.POWER});

        DataTestOptionsDTO dataTestOptionsDTO =
                dataTestOptionsMapperImpl.toDTO(dataTestOptions);

        assertThat(dataTestOptionsDTO.getDelayInSeconds())
                .isEqualTo(10);
        assertThat(dataTestOptionsDTO.getMeasurementTypes())
                .containsExactly(MeasurementType.TEMPERATURE,
                        MeasurementType.POWER);

        DataTestOptions mappedDataTestOptions =
                dataTestOptionsMapperImpl.toEntity(dataTestOptionsDTO);

        assertThat(mappedDataTestOptions.getDelayInSeconds()).isEqualTo(10);
        assertThat(mappedDataTestOptions.getMeasurementTypes())
                .containsExactly(MeasurementType.TEMPERATURE,
                        MeasurementType.POWER);
    }
}
