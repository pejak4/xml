package com.example.carService.model;

import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
public class RatingCarRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;
    @ToString.Exclude
    private Long fromUserId;
    @ToString.Exclude
    private Long carId;
    private double rating;

}
