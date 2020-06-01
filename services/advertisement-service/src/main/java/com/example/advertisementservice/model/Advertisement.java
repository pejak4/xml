package com.example.advertisementservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    private Long advertiserId;
    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;

    @OneToMany(mappedBy = "advertisement")
    private Set<Comment> comments;

    private Long pricelistId;


}