package org.example.web.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.model.measurementType.MeasurementType;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class DataDTO {
    private Long sensorId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timeStamp;
    private Double measurement;
    private MeasurementType measurementType;

}
