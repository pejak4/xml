package com.example.carService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class CarRentalRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startDate;
    private Timestamp endDate;
    private String userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private Car rentalRequestCar;
}
