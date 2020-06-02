package com.example.searchCarservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarFilterDTO {

    private String brand;
    private String model;
    private String fuelType;
    private String transmission;
    private String classCar;
    private double[] price;
    private String mileage;
    private String plannedMileage;
    @JsonProperty
    private Boolean CDW;
    private String capacitySeatsForKids;
    private String cityLocation;
    private Timestamp startDate;
    private Timestamp endDate;
}
