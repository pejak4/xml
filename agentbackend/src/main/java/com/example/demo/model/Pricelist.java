package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pricelist{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int monday;
    private int tuesday;
    private int wednesday;
    private int thursday;
    private int friday;
    private int saturday;
    private int sunday;

    private int cdw;
    private int kilometer;

    private long secondId;

    @OneToMany(mappedBy = "pricelist", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Car> pricelistCars;
}
