package com.example.codebook.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Brand brand;
}
