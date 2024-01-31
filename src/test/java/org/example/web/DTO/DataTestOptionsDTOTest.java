package org.example.web.DTO;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.measurementType.MeasurementType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DataTestOptionsDTOTest {
    @Test
    public void testGettersAndSetters() {
        DataTestOptionsDTO dataTestOptionsDTO = new DataTestOptionsDTO();

        dataTestOptionsDTO.setDelayInSeconds(10);
        dataTestOptionsDTO
                .setMeasurementTypes(new MeasurementType[]{MeasurementType.TEMPERATURE,
                        MeasurementType.VOLTAGE});

        assertThat(dataTestOptionsDTO.getDelayInSeconds()).isEqualTo(10);
        assertThat(dataTestOptionsDTO.getMeasurementTypes())
                .containsExactly(MeasurementType.TEMPERATURE, MeasurementType.VOLTAGE);
    }
    @Test
    public void testSerialization() throws Exception {
        // Given
        DataTestOptionsDTO dataTestOptionsDTO = new DataTestOptionsDTO();
        dataTestOptionsDTO.setDelayInSeconds(10);
        dataTestOptionsDTO
                .setMeasurementTypes(new MeasurementType[]{MeasurementType.TEMPERATURE,
                        MeasurementType.POWER});

        String jsonString = new ObjectMapper().writeValueAsString(dataTestOptionsDTO);

        String expectedJson =
                "{\"delayInSeconds\":10,\"measurementTypes\":[\"TEMPERATURE\",\"POWER\"]}";
        assertThat(jsonString).isEqualTo(expectedJson);
    }
    @Test
    public void testDeserialization() throws Exception {
        String jsonInput =
                "{\"delayInSeconds\":10,\"measurementTypes\":[\"TEMPERATURE\",\"POWER\"]}";

        DataTestOptionsDTO deserializedDataTestOptionsDTO = new ObjectMapper().readValue(jsonInput, DataTestOptionsDTO.class);

        assertThat(deserializedDataTestOptionsDTO.getDelayInSeconds()).isEqualTo(10);
        assertThat(deserializedDataTestOptionsDTO.getMeasurementTypes())
                .containsExactly(MeasurementType.TEMPERATURE, MeasurementType.POWER);
    }
}
