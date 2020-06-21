package com.example.demo.repository;

import com.example.demo.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvertisementRepository extends  JpaRepository<Car, Long>  {
    Car findOneById(Long id);
    List<Car> findAllByUserId(String user_id);
}
