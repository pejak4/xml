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
@ToString
public class CarRentalDate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ToString.Exclude
    private Long id;

    private Timestamp startDate;
    private Timestamp endDate;
    @ToString.Exclude
    private Long fromUserId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Car car;
}
