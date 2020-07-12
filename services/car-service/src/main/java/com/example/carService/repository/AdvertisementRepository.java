package com.example.carService.repository;

import com.example.carService.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepository extends  JpaRepository<Car, Long>  {

    Car findOneById(Long id);
}
