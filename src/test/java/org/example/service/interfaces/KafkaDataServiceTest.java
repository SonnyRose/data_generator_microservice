package org.example.service.interfaces;

import org.example.model.Data;
import org.example.service.implementation.KafkaDataServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.kafka.sender.KafkaSender;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;


public class KafkaDataServiceTest {
    @Mock
    private KafkaSender<String, Object> mockSender;
    @InjectMocks
    private KafkaDataServiceImpl kafkaDataService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testSend_withNullData_throwsException() {
        Data testData = null;

        assertThrows(IllegalArgumentException.class, () -> kafkaDataService.send(testData));
        verifyNoInteractions(mockSender);
    }
}
