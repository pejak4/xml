package com.example.carService.repository;

import com.example.carService.model.Car;
import com.example.carService.model.CarRentalRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RentalRequestRepository extends  JpaRepository<CarRentalRequest, Long>  {


    List<CarRentalRequest> findAll();
    CarRentalRequest findOneByUserIdAndRentalRequestCarId(String user_id, Long rental_request_car_id);
    CarRentalRequest findOneById(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from car_rental_request crr where crr.id=:id")
    void deleteRentalRequest(@Param("id") Long id);
}
