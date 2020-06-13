package com.example.carService.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int doors;
    private String description;
    private String image;
    private String brand;
    private String model;
    private String fuelType;
    private String transmission;
    private String classCar;
    private double price;
    private int mileage;
    private int plannedMileage;
    private Boolean CDW;
    private int capacitySeats;
    private int capacitySeatsForKids;
    private int cubicCapacity;
    private int horsePower;
    private Boolean usb;
    private Boolean gps;
    private double fuelTankCapacity;
    private String cityLocation;

    private String userId;

    private double rating;
    private int numOfRating;

    @OneToMany(mappedBy = "car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<CarRentalDate> dateList;

    @OneToMany(mappedBy = "rentalRequestCar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<CarRentalRequest> rentalRequestsList;

    @OneToMany(mappedBy = "occupancyCar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Occupancy> occupancyList;
}
