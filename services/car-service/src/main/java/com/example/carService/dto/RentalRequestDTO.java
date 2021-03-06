package com.example.carService.dto;

import com.example.carService.model.Car;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestDTO {
    private Long carId;
    private Timestamp startData;
    private Timestamp endData;
    private String userId;
}
