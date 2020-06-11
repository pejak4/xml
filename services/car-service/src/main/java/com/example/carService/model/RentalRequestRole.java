package com.example.carService.model;

import lombok.Getter;

@Getter
public enum RentalRequestRole {

    PENDING("PENDING"), RESERVED("RESERVED"), PAID("PAID"), CANCELED("CANCELED");

    private String role;


    RentalRequestRole(String role) {
        this.role = role;
    }
}
