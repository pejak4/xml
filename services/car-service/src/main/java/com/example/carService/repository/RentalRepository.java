package com.example.carService.repository;

import com.example.carService.model.CarRentalDate;
import com.example.carService.model.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RentalRepository extends  JpaRepository<CarRentalDate, Long>  {

//    @Query(nativeQuery = true, value = "select c.id, c.start_date, c.end_date, c.from_user_id, c.car_id from car_rental_date c " +
//            "where c.from_user_id=:from_user_id and c.end_date<:end_date and c.car_id=:car_id ")
//    List<CarRentalDate> findAllRentalFrom(@Param("from_user_id") Long from_user_id, @Param("end_date") Timestamp end_date,
//                                            @Param("car_id") Long car_id);
}
