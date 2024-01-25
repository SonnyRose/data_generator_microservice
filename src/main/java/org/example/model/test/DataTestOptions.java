package org.example.model.test;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.example.model.measurementType.MeasurementType;

@NoArgsConstructor
@Getter
@Setter
public class DataTestOptions {
    private Integer delayInSeconds;
    private MeasurementType[] measurementTypes;
}
