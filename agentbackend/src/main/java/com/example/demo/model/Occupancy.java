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
public class Occupancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp startDate;
    private Timestamp endDate;

    private Long secondId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private Car occupancyCar;
}
