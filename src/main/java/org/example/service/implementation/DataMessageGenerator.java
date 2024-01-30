package org.example.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.model.Data;
import org.example.model.measurementType.MeasurementType;
import org.example.model.test.DataTestOptions;
import org.example.service.interfaces.KafkaDataService;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class DataMessageGenerator implements Runnable {
    private final DataTestOptions dataTestOptions;
    private final KafkaDataService kafkaDataService;
    @Override
    public void run() {
        Data data = generatorData();
        kafkaDataService.send(data);
    }
    private Data generatorData() {
        Data data = new Data();
        data.setSensorId((long) getRandomNumber(1, 10));
        data.setMeasurement(getRandomNumber(15, 100));
        data.setMeasurementType(getRandomMeasurementType(dataTestOptions.getMeasurementTypes()));
        data.setTimeStamp(LocalDateTime.now());
        return data;
    }
    private static double getRandomNumber(double min, double max) {
        return (Math.random() * (max - min)) + min;
    }
    private static MeasurementType getRandomMeasurementType(MeasurementType[] measurementTypes) {
        if (measurementTypes == null) {
            throw new IllegalArgumentException("Measurement types cannot be null");
        }
        int randomTypeId = (int) (Math.random() * measurementTypes.length);
        return measurementTypes[randomTypeId];
    }
}
