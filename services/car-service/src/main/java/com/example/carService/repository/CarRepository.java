package com.example.carService.repository;

import com.example.carService.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.sql.Timestamp;
import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {

    @Query(nativeQuery = true, value = "select c.id, c.doors, c.description, c.image, c.brand, c.model, c.fuel_type, " +
            "c.transmission, c.class_car, c.price, c.mileage, c.planned_mileage, c.cdw, c.capacity_seats, " +
            "c.capacity_seats_for_kids, c.cubic_capacity, c.horse_power, c.usb, c.gps, c.fuel_tank_capacity, " +
            "c.city_location from car_rental_date crd left join car c on c.id = crd.car_id where c.city_location=:city_location " +
            "and (crd.start_date>:end_date or crd.end_date<:start_date) union (select cc.id, cc.doors, cc.description, " +
            "cc.image, cc.brand, cc.model, cc.fuel_type, cc.transmission, cc.class_car, cc.price, cc.mileage, " +
            "cc.planned_mileage, cc.cdw, cc.capacity_seats, cc.capacity_seats_for_kids, cc.cubic_capacity, " +
            "cc.horse_power, cc.usb, cc.gps, cc.fuel_tank_capacity, cc.city_location from car cc left join car_rental_date dd " +
            "on cc.id = dd.id where dd.id is null and cc.city_location=:city_location)")
    List<Car> findCarSearch(@Param("start_date") Timestamp start_date, @Param("end_date") Timestamp end_date,
                            @Param("city_location") String city_location);

    @Query(nativeQuery = true, value = "(select c.id from car c where c.brand=:brand union select c.id from car c where ''=:brand) INTERSECT \n" +
            "(select c.id from car c where c.model=:model union select c.id from car c where ''=:model) INTERSECT\n" +
            "(select c.id from car c where c.fuel_type=:fuel_type union select c.id from car c where ''=:fuel_type) INTERSECT\n" +
            "(select c.id from car c where c.transmission=:transmission union select c.id from car c where ''=:transmission) INTERSECT\n" +
            "(select c.id from car c where c.class_car=:class_car union select c.id from car c where ''=:class_car) INTERSECT\n" +
            "(select c.id from car c where c.price>=:min_price and c.price <=:max_price) INTERSECT\n" +
            "(select c.id from car c where c.mileage<=:mileage union select c.id from car c where 0=:mileage) INTERSECT\n" +
            "(select c.id from car c where c.planned_mileage>=:planned_mileage union select c.id from car c where 0=:planned_mileage) INTERSECT\n" +
            "(select c.id from car c where c.CDW=:CDW) INTERSECT\n" +
            "(select c.id from car c where c.capacity_seats_for_kids>=:capacity_seats_for_kids union select c.id from car c where 0=:capacity_seats_for_kids) INTERSECT\n" +
            "(select c.id from car_rental_date crd left join car c on c.id = crd.car_id where c.city_location=:city_location \n" +
            "and (crd.start_date>:end_date or crd.end_date<:start_date) union (select cc.id from car cc left join car_rental_date dd\n" +
            "on cc.id = dd.id where dd.id is null and cc.city_location=:city_location))")
    List<Long> findCarFilter(@Param("brand") String brand, @Param("model") String model, @Param("fuel_type") String fuel_type,
                            @Param("transmission") String transmission, @Param("class_car") String class_car,
                            @Param("min_price") Double min_price, @Param("max_price") Double max_price,
                            @Param("mileage") Integer mileage, @Param("planned_mileage") Integer planned_mileage,
                            @Param("CDW") Boolean CDW, @Param("capacity_seats_for_kids") Integer capacity_seats_for_kids,
                             @Param("city_location") String city_location, @Param("start_date") Timestamp start_date,
                             @Param("end_date") Timestamp end_date);
}
