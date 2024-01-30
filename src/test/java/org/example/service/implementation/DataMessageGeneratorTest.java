package org.example.service.implementation;

import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;
import org.junit.jupiter.api.BeforeEach;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DataMessageGeneratorTest {
    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        when(dataTestOptions.getMeasurementTypes()).thenReturn(MeasurementType.values());
    }
    @Mock
    private DataTestOptions dataTestOptions;
    @Mock
    private KafkaDataService kafkaDataService;
    @InjectMocks
    private DataMessageGenerator dataMessageGenerator;

    @Test
    public void testRun_sendsGeneratedDataToKafkaService(){
        dataMessageGenerator.run();
        verify(kafkaDataService, times(1)).send(any(Data.class));
    }
    @Test
    public void testGeneratorData_generatesDataWithValidProperties(){
        assertDoesNotThrow(() -> dataMessageGenerator.run());
    }
    @Test
    public void testRun_generatesDataWithValidMeasurementRange() {
        Data generatedData = captureSentData();

        double measurement = generatedData.getMeasurement();
        assertTrue(measurement >= 15 && measurement <= 100, "Measurement should be within the valid range");

        verify(kafkaDataService, times(1)).send(any(Data.class));
    }
    @Test
    public void testRun_generatesDataWithNonNullTimeStamp() {
        Data generatedData = captureSentData();

        assertNotNull(generatedData.getTimeStamp(), "Timestamp should not be null");

        verify(kafkaDataService, times(1)).send(any(Data.class));
    }
    @Test
    public void testRun_handlesNullMeasurementTypes() {
        when(dataTestOptions.getMeasurementTypes()).thenReturn(null);
        assertThrows(IllegalArgumentException.class, () -> dataMessageGenerator.run());
    }
    @Test
    public void testRun_generatesDataWithSpecificMeasurementType() {
        MeasurementType expectedType = MeasurementType.TEMPERATURE;
        when(dataTestOptions.getMeasurementTypes()).thenReturn(new MeasurementType[]{expectedType});

        Data generatedData = captureSentData();
        assertEquals(expectedType, generatedData.getMeasurementType());
    }
    private Data captureSentData() {
        ArgumentCaptor<Data> dataCaptor = ArgumentCaptor.forClass(Data.class);
        dataMessageGenerator.run();
        verify(kafkaDataService).send(dataCaptor.capture());
        return dataCaptor.getValue();
    }
}




