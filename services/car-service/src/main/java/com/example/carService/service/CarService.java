package com.example.carService.service;

import com.example.carService.model.Car;
import com.example.carService.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    public List<Car> findCarSearch(Timestamp startDate, Timestamp endDate, String cityLocation){
        return this.carRepository.findCarSearch(startDate, endDate, cityLocation);
    }

    public List<Car> findCarFilter(String brand, String model, String fuel_type, String transmission, String class_car,
                                   Double min_price, Double max_price, String mileageS, String planned_mileageS,
                                   Boolean CDW, String capacity_seats_for_kidsS, String city_location,
                                   Timestamp start_date, Timestamp end_date) {

        Integer mileage = mileageS.equals("") ? 0 : Integer.parseInt(mileageS);
        Integer planned_mileage = planned_mileageS.equals("") ? 0 : Integer.parseInt(planned_mileageS);
        Integer capacity_seats_for_kids = capacity_seats_for_kidsS.equals("") ? 0 : Integer.parseInt(capacity_seats_for_kidsS);

        List<Long> carsId = this.carRepository.findCarFilter(brand, model, fuel_type, transmission, class_car, min_price,
                                                max_price, mileage, planned_mileage, CDW, capacity_seats_for_kids,
                                                city_location, start_date, end_date);

        return this.carRepository.findAllById(carsId);
    }

    public Optional<Car> findOneCar(Long id) {
        return this.carRepository.findById(id);
    }
}
