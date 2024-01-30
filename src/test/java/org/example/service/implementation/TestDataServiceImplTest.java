package org.example.service.implementation;

import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class TestDataServiceImplTest {
    @Mock
    private KafkaDataService kafkaDataService;

    @Mock
    private ScheduledExecutorService executorService;

    @InjectMocks
    private TestDataServiceImpl testDataService;

    @Captor
    private ArgumentCaptor<Data> dataCaptor;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        doAnswer(invocation -> {
            Runnable task = invocation.getArgument(0);
            task.run();
            return null;
        }).when(executorService).scheduleAtFixedRate(any(Runnable.class), anyLong(), anyLong(), any(TimeUnit.class));
    }

    @Test
    void sendMessage_withValidOptions_sendsDataToKafka() {
        DataTestOptions testOptions = new DataTestOptions();
        testOptions.setDelayInSeconds(1);
        testOptions.setMeasurementTypes(new MeasurementType[]{MeasurementType.TEMPERATURE});

        testDataService.sendMessage(testOptions);

        verify(kafkaDataService, atLeastOnce()).send(dataCaptor.capture());
        Data sentData = dataCaptor.getValue();

        assertNotNull(sentData.getSensorId());
        assertTrue(sentData.getMeasurement() >= 15 && sentData.getMeasurement() <= 20);
        assertEquals(MeasurementType.TEMPERATURE, sentData.getMeasurementType());
        assertNotNull(sentData.getTimeStamp());
    }

    @ParameterizedTest
    @MethodSource("delayAndMeasurementTypesProvider")
    void sendMessage_withDifferentDelaysAndMeasurementTypes_sendsDataCorrectly(
            int delayInSeconds, MeasurementType[] measurementTypes
    ) {

    }

    static Stream<Arguments> delayAndMeasurementTypesProvider() {
        return Stream.of(
                Arguments.of(2, new MeasurementType[]{MeasurementType.VOLTAGE})
        );
    }

    @Test
    void sendMessage_withEmptyMeasurementTypes_doesNotScheduleTask() {
        DataTestOptions testOptions = new DataTestOptions();

        testOptions.setMeasurementTypes(new MeasurementType[0]);

        testDataService.sendMessage(testOptions);

        verifyNoInteractions(executorService);
    }
}
