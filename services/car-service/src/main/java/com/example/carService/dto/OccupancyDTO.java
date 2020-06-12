package com.example.carService.dto;

import com.example.carService.model.Car;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OccupancyDTO {
    private Timestamp startDate;
    private Timestamp endDate;
    private Long carId;
}
