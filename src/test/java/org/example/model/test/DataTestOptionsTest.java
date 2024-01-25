package org.example.model.test;

import org.example.model.measurementType.MeasurementType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DataTestOptionsTest {
    @Test
    void testSetAndGetDelayInSeconds() {
        DataTestOptions dataTestOptions = new DataTestOptions();
        Integer expectedDelayInSeconds = 10;

        dataTestOptions.setDelayInSeconds(expectedDelayInSeconds);
        Integer actualDelayInSeconds = dataTestOptions.getDelayInSeconds();

        assertEquals(expectedDelayInSeconds, actualDelayInSeconds);
    }
    @Test
    void testSetAndGetMeasurementTypes() {
        DataTestOptions dataTestOptions = new DataTestOptions();
        MeasurementType[] expectedMeasurementTypes = {MeasurementType.TEMPERATURE, MeasurementType.VOLTAGE};

        dataTestOptions.setMeasurementTypes(expectedMeasurementTypes);
        MeasurementType[] actualMeasurementTypes = dataTestOptions.getMeasurementTypes();

        assertArrayEquals(expectedMeasurementTypes, actualMeasurementTypes);
    }
}
