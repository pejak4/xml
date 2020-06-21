package com.example.demo.model;

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

    private Long secondId;

    private Boolean checkMileage;//Da upisujemo kilometrazu nakon rentanja ali samo jednom!

    @ManyToOne(fetch = FetchType.EAGER)
    private Car car;
}
