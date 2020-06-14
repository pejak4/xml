package com.example.carService.model;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RatingCarRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long fromUserId;

    private Long carId;
    private double rating;

}
