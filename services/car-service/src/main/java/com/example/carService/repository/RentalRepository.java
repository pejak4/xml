package com.example.carService.repository;

import com.example.carService.model.CarRentalDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends  JpaRepository<CarRentalDate, Long>  {
}
