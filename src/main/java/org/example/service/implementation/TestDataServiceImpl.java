package org.example.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;
import org.example.service.interfaces.TestDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



@Service
@RequiredArgsConstructor
public class TestDataServiceImpl implements TestDataService {
    private final ScheduledExecutorService executorService
            = Executors.newSingleThreadScheduledExecutor();
    private final KafkaDataService kafkaDataService;

    @Override
    public void sendMessage(DataTestOptions testOptions) {
        if (testOptions.getMeasurementTypes() == null){
            throw new IllegalArgumentException("DataTestOption or Measurement types cannot be null");
        }
        if (testOptions.getMeasurementTypes().length > 0) {
            executorService.scheduleAtFixedRate(
                    () -> {
                        Data data = new Data();
                        data.setSensorId(
                                (long) getRandomNumber(1, 10)
                        );
                        data.setMeasurement(
                                getRandomNumber(15, 20)
                        );
                        data.setMeasurementType(
                                getRandomMeasurement(
                                        testOptions.getMeasurementTypes()
                                )
                        );
                        data.setTimeStamp(
                                LocalDateTime.now()
                        );
                        kafkaDataService.send(data);
                    },
                    0,
                    testOptions.getDelayInSeconds(),
                    TimeUnit.SECONDS
            );
    }
}
    private double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }

    private MeasurementType getRandomMeasurement(
            MeasurementType[] measurementTypes
    ) {
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }
}
