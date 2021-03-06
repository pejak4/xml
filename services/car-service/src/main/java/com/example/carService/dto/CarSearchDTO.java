package com.example.carService.dto;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarSearchDTO {

    private String startCity;
    private String endCity;
    private Timestamp startDate;
    private Timestamp endDate;
}
