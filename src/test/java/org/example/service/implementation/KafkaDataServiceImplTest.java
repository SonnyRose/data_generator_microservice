package org.example.service.implementation;


import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import reactor.kafka.sender.KafkaSender;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KafkaDataServiceImplTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private KafkaSender<String, Object> mockSender;

    @InjectMocks
    private KafkaDataServiceImpl kafkaDataService;

    @Test
    public void testSend_sendsToCorrectTopic() {
        Data testData = createTestData(MeasurementType.TEMPERATURE, 10.0);
        assertDoesNotThrow(() -> kafkaDataService.send(testData));
        assertNotNull(testData);
    }
    @Test
    public void testSend_nullData_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> kafkaDataService.send(null));
        verifyNoInteractions(mockSender);
    }
    private Data createTestData(MeasurementType measurementType, Double measurementValue) {
        Data testData = new Data();
        testData.setMeasurementType(measurementType);
        testData.setMeasurement(measurementValue);

        long timestamp = System.currentTimeMillis();
        LocalDateTime localDateTime = Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        testData.setTimeStamp(localDateTime);

        return testData;
    }
}
