package org.example.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Data;
import org.example.service.interfaces.KafkaDataService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaDataServiceImpl implements KafkaDataService {
    private final KafkaSender<String, Object> sender;
    @Override
    public void send(Data data) {
        Objects.requireNonNull(data, "Data cannot be null");
        validateMeasurementValue(data);
        String topic = switch (data.getMeasurementType()) {
            case TEMPERATURE -> "data-temperature";
            case VOLTAGE -> "data-voltage";
            case POWER -> "data-power";
        };
        Flux<?> result = sender.send(
                Mono.just(
                        SenderRecord.create(
                                topic,
                                0,
                                System.currentTimeMillis(),
                                String.valueOf(data.hashCode()),
                                data,
                                null
                        )
                )
        );
        handleSendResult(result);
    }
    private void validateMeasurementValue(Data data) {
        try {
            Double.parseDouble(String.valueOf(data.getMeasurement()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid measurement value: " + data.getMeasurement(), e);
        }
    }
    private void handleSendResult(Flux<?> result) {
        if (result != null) {
            result.doOnError(Throwable::printStackTrace).subscribe();
        } else {
            log.error("KafkaSender.send() returned null, indicating a configuration or setup issue.");
        }
    }
}
