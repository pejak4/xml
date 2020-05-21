package com.project.searchservice.repository;

import com.project.searchservice.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    //select * from car c inner join car_rental_date crd on c.id = crd.car_id where c.city_location = 'Novi Sad' and (crd.start_date > '2020-02-01 01:01:01' or crd.end_date < '2020-02-01 01:01:01')
    @Query(nativeQuery = true, value = "select c.id, c.doors, c.description, c.image, c.brand, c.model, c.fuel_type, " +
            "c.transmission, c.class_car, c.price, c.mileage, c.planned_mileage, c.cdw, c.capacity_seats, " +
            "c.capacity_seats_for_kids, c.cubic_capacity, c.horse_power, c.usb, c.gps, c.fuel_tank_capacity, " +
            "c.city_location from car_rental_date crd left join car c on c.id = crd.car_id " +
            "where c.city_location=:city_location and (crd.start_date>:end_date or crd.end_date<:start_date) " +
            "union (select cc.id, cc.doors, cc.description, cc.image, cc.brand, cc.model, cc.fuel_type, cc.transmission," +
            " cc.class_car, cc.price, cc.mileage, cc.planned_mileage, cc.cdw, cc.capacity_seats, cc.capacity_seats_for_kids, " +
            "cc.cubic_capacity, cc.horse_power, cc.usb, cc.gps, cc.fuel_tank_capacity, cc.city_location from car cc " +
            "left join car_rental_date dd on cc.id = dd.id where dd.id is null and cc.city_location=:city_location)")
    List<Car> findCarSearch(@Param("start_date") Timestamp start_date, @Param("end_date") Timestamp end_date, @Param("city_location") String city_location);

}
