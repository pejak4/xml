package com.example.advertisementservice.Repository;

import com.example.advertisementservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdvertisementRepository extends  JpaRepository<Car, Long>  {
}
