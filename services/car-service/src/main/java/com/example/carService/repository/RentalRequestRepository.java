package com.example.carService.repository;

import com.example.carService.model.Car;
import com.example.carService.model.CarRentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRequestRepository extends  JpaRepository<CarRentalRequest, Long>  {


    List<CarRentalRequest> findAll();
    CarRentalRequest findOneByUserIdAndRentalRequestCarId(String user_id, Long rental_request_car_id);
}
