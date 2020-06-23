package com.example.demo.repository;

import com.example.demo.model.CarRentalDate;
//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface RentalRepository extends JpaRepository<CarRentalDate, Long> {

    //Nadjemo sv auto koja se vise ne rentaju a za koja nije upisana kilometraza(check_milage = false)
    @Query(nativeQuery = true, value = "select c.second_id, c.check_mileage, c.id, c.start_date, c.end_date, c.from_user_id, c.car_id from car_rental_date c " +
            "where c.from_user_id=:from_user_id and c.end_date<:end_date and c.check_mileage=:check_mileage")
    List<CarRentalDate> findAllCarInRentalEnd(@Param("from_user_id") Long from_user_id, @Param("end_date") Timestamp end_date,
                                              @Param("check_mileage") Boolean check_mileage);
}
