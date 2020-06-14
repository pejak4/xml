package com.example.carService.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RatingCarRequestDTO {
    private Long fromUserId;
    private Long carId;
    private double rating;
}
