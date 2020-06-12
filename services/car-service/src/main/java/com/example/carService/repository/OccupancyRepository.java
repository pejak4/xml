package com.example.carService.repository;

import com.example.carService.model.Car;
import com.example.carService.model.Occupancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface OccupancyRepository extends JpaRepository<Occupancy, Long> {

    @Query(nativeQuery = true, value = "select c.id, c.start_date, c.end_date, c.occupancy_car_id from occupancy c " +
            "where c.occupancy_car_id=:occupancy_car_id and ( not(c.start_date>:end_date or c.end_date<:start_date))")
    List<Occupancy> findOccupancyAndRequest(@Param("start_date") Timestamp start_date, @Param("end_date") Timestamp end_date,
                            @Param("occupancy_car_id") Long occupancy_car_id);
}
