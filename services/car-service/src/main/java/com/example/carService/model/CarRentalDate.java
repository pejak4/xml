package com.example.carService.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CarRentalDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startDate;
    private Timestamp endDate;

    private Long fromUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Car car;
}
