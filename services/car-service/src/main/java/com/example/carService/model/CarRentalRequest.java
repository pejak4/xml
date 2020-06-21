package com.example.carService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String forUserId;

    private Timestamp createDate;

    private Boolean agent;

    @Enumerated(EnumType.STRING)
    private RentalRequestRole role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonManagedReference
    private Car rentalRequestCar;
}
