package com.example.carService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalRequestIdAndCarDTO {
    private String userId;
    private String carId;
}
