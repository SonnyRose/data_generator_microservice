package org.example.web.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.measurementType.MeasurementType;

@NoArgsConstructor
@Getter
@Setter
public class DataTestOptionsDTO {
    private Integer delayInSeconds;
    private MeasurementType[] measurementTypes;
}
