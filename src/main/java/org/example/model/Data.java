package org.example.model;

import lombok.*;
import org.example.model.measurementType.MeasurementType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Data {
    private Long sensorId;
    private LocalDateTime timeStamp;
    private Double measurement;
    private MeasurementType measurementType;
}
